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


# CRUD OPERATIONS by SIDHARTH


@app.route('/dashboard')
@app.route('/')
def dashboard():
    students = Student.query.all()
    courses = Course.query.all()
    return render_template("index.html", students=students, courses=courses)

@app.route('/insert', methods=['POST'])
def insert():
    name = request.form['name']
    course_id = int(request.form['course_id'])
    email = request.form['email']

    new_student = Student(name, course_id, email)
    db.session.add(new_student)
    db.session.commit()
    return redirect(url_for('dashboard'))

@app.route('/update', methods=['POST'])
def update():
    my_data = Student.query.get(request.form['id'])
    my_data.name = request.form['name']
    my_data.course_id = int(request.form['course_id'])
    my_data.email = request.form['email']

    db.session.commit()
    return redirect(url_for('dashboard'))

@app.route('/delete/<int:id>')
def delete(id):
    my_data = Student.query.get(id)
    db.session.delete(my_data)
    db.session.commit()
    return redirect(url_for('dashboard'))

@app.route('/course/insert', methods=['POST'])
def insert_course():
    course_name = request.form['course_name']
    credits = int(request.form['credits'])

    new_course = Course(course_name, credits)
    db.session.add(new_course)
    db.session.commit()
    return redirect(url_for('dashboard'))

@app.route('/course/update', methods=['POST'])
def update_course():
    course = Course.query.get(request.form['id'])
    course.course_name = request.form['course_name']
    course.credits = int(request.form['credits'])

    db.session.commit()
    return redirect(url_for('dashboard'))

@app.route('/course/delete/<int:id>')
def delete_course(id):
    course = Course.query.get(id)
    db.session.delete(course)
    db.session.commit()
    return redirect(url_for('dashboard'))

if __name__ == "__main__":
    with app.app_context():
        db.create_all()
    app.run(debug=True)
