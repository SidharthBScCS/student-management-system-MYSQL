from flask import Flask, render_template, request, redirect, url_for
from flask_sqlalchemy import SQLAlchemy

app = Flask(__name__)
app.config['SQLALCHEMY_DATABASE_URI'] = 'mysql+pymysql://root@localhost/student_management_system'
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False

db = SQLAlchemy(app)

class Course(db.Model):
    __tablename__ = 'course'

    id = db.Column(db.Integer, primary_key=True)
    course_name = db.Column(db.String(100), nullable=False)
    credits = db.Column(db.Integer, nullable=False)
    students = db.relationship('Student', backref='course', lazy=True)

    def __init__(self, course_name, credits):
        self.course_name = course_name
        self.credits = credits


class Student(db.Model):
    __tablename__ = 'student'

    id = db.Column(db.Integer, primary_key=True)
    name = db.Column(db.String(100), nullable=False)
    course_id = db.Column(db.Integer, db.ForeignKey('course.id'), nullable=False)
    email = db.Column(db.String(100), nullable=False)

    def __init__(self, name, course_id, email):
        self.name = name
        self.course_id = course_id
        self.email = email


@app.route('/')
@app.route('/students')
def students():
    students = Student.query.all()
    courses = Course.query.all()
    return render_template('students.html', students=students, courses=courses)

@app.route('/courses')
def courses():
    courses = Course.query.all()
    return render_template('courses.html', courses=courses)

@app.route('/students/add', methods=['POST'])
def add_student():
    new_student = Student(
        request.form['name'],
        int(request.form['course_id']),
        request.form['email']
    )
    db.session.add(new_student)
    db.session.commit()
    return redirect(url_for('students'))

@app.route('/students/update/<int:id>', methods=['POST'])
def update_student(id):
    student = Student.query.get_or_404(id)
    student.name = request.form['name']
    student.course_id = int(request.form['course_id'])
    student.email = request.form['email']
    db.session.commit()
    return redirect(url_for('students'))

@app.route('/students/delete/<int:id>')
def delete_student(id):
    student = Student.query.get_or_404(id)
    db.session.delete(student)
    db.session.commit()
    return redirect(url_for('students'))

@app.route('/courses/add', methods=['POST'])
def add_course():
    new_course = Course(
        request.form['course_name'],
        int(request.form['credits'])
    )
    db.session.add(new_course)
    db.session.commit()
    return redirect(url_for('courses'))

@app.route('/courses/update/<int:id>', methods=['POST'])
def update_course(id):
    course = Course.query.get_or_404(id)
    course.course_name = request.form['course_name']
    course.credits = int(request.form['credits'])
    db.session.commit()
    return redirect(url_for('courses'))

@app.route('/courses/delete/<int:id>')
def delete_course(id):
    course = Course.query.get_or_404(id)
    Student.query.filter_by(course_id=id).delete()
    db.session.delete(course)
    db.session.commit()
    return redirect(url_for('courses'))

if __name__ == "__main__":
    with app.app_context():
        db.create_all()
    app.run(debug=True)
