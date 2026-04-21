from flask import Flask, render_template, request, redirect, url_for
from flask_sqlalchemy import SQLAlchemy

app = Flask(__name__)
app.config['SQLALCHEMY_DATABASE_URI'] = 'mysql+pymysql://root@localhost/student_management_system'
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False

db = SQLAlchemy(app)

class Student(db.Model):
    __tablename__ = 'student'

    id = db.Column(db.Integer, primary_key=True)
    name = db.Column(db.String(100), nullable=False)
    course = db.Column(db.String(100), nullable=False)
    email = db.Column(db.String(100), nullable=False)
    phone = db.Column(db.String(20), nullable=False)

    def __init__(self, name, course, email, phone):
        self.name = name
        self.course = course
        self.email = email
        self.phone = phone


# CRUD OPERATIONS by SIDHARTH


@app.route('/dashboard')
@app.route('/')
def dashboard():
    students = Student.query.all()
    return render_template("index.html", students=students)

@app.route('/insert', methods=['POST'])
def insert():
    name = request.form['name']
    course = request.form['course']
    email = request.form['email']
    phone = request.form['phone']

    new_student = Student(name, course, email, phone)
    db.session.add(new_student)
    db.session.commit()
    return redirect(url_for('dashboard'))

@app.route('/update', methods=['POST'])
def update():
    my_data = Student.query.get(request.form['id'])
    my_data.name = request.form['name']
    my_data.course = request.form['course']
    my_data.email = request.form['email']
    my_data.phone = request.form['phone']

    db.session.commit()
    return redirect(url_for('dashboard'))

@app.route('/delete/<int:id>')
def delete(id):
    my_data = Student.query.get(id)
    db.session.delete(my_data)
    db.session.commit()
    return redirect(url_for('dashboard'))

if __name__ == "__main__":
    with app.app_context():
        db.create_all()
    app.run(debug=True)
