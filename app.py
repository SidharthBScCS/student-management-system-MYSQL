from flask import Flask, render_template, request, redirect, url_for
from flask_sqlalchemy import SQLAlchemy

app = Flask(__name__)
app.config['SQLALCHEMY_DATABASE_URI'] = 'mysql+pymysql://root@localhost/flask_login'
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False

db = SQLAlchemy(app)

class Data(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    name = db.Column(db.String(100), nullable=False)
    student_class = db.Column(db.Integer, nullable=False)
    age = db.Column(db.Integer, nullable=False)
    email = db.Column(db.String(100), nullable=False)

    def __init__(self, name, student_class, age, email):
        self.name = name
        self.student_class = student_class
        self.age = age
        self.email = email


# CRUD OPERATIONS by SIDHARTH


@app.route('/dashboard')
@app.route('/')
def dashboard():
    all_data = Data.query.all()
    return render_template("index.html", employees=all_data)

@app.route('/insert', methods=['POST'])
def insert():
    name = request.form['name']
    student_class = int(request.form['student_class'])
    age = int(request.form['age'])
    email = request.form['email']

    new_student = Data(name, student_class, age, email)
    db.session.add(new_student)
    db.session.commit()
    return redirect(url_for('dashboard'))

@app.route('/update', methods=['POST'])
def update():
    my_data = Data.query.get(request.form['id'])
    my_data.name = request.form['name']
    my_data.student_class = int(request.form['student_class'])
    my_data.age = int(request.form['age'])
    my_data.email = request.form['email']

    db.session.commit()
    return redirect(url_for('dashboard'))

@app.route('/delete/<int:id>')
def delete(id):
    my_data = Data.query.get(id)
    db.session.delete(my_data)
    db.session.commit()
    return redirect(url_for('dashboard'))

if __name__ == "__main__":
    with app.app_context():
        db.create_all()
    app.run(debug=True)
