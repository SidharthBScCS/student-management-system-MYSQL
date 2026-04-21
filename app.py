from flask import Flask, render_template, request, redirect, url_for, flash
from flask_sqlalchemy import SQLAlchemy
from flask_login import LoginManager, login_user, login_required, logout_user, UserMixin, current_user
from flask_bcrypt import Bcrypt
from flask_wtf import FlaskForm
from wtforms import StringField, PasswordField, SubmitField
from wtforms.validators import InputRequired, Length, ValidationError, EqualTo

app = Flask(__name__)
app.config['SQLALCHEMY_DATABASE_URI'] = 'mysql+pymysql://root@localhost/flask_login'
app.config['SECRET_KEY'] = 'sidharth'
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False

db = SQLAlchemy(app)
bcrypt = Bcrypt(app)

login_manager = LoginManager()
login_manager.init_app(app)
login_manager.login_view = 'login'


class User(db.Model, UserMixin):
    id = db.Column(db.Integer, primary_key=True)
    username = db.Column(db.String(100), nullable=False, unique=True)
    password = db.Column(db.String(200), nullable=False)

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


@login_manager.user_loader
def load_user(user_id):
    return User.query.get(int(user_id))


class RegisterForm(FlaskForm):
    username = StringField(validators=[InputRequired(), Length(min=4, max=100)])
    password = PasswordField(validators=[InputRequired(), Length(min=4, max=100)])
    confirm_password = PasswordField(validators=[InputRequired(), EqualTo('password')])
    submit = SubmitField("Register")

    def validate_username(self, username):
        if User.query.filter_by(username=username.data).first():
            raise ValidationError("Username already exists.")

class LoginForm(FlaskForm):
    username = StringField(validators=[InputRequired(), Length(min=4, max=100)])
    password = PasswordField(validators=[InputRequired(), Length(min=4, max=100)])
    submit = SubmitField("Login")


# User Authentication by SIDHARTH 

@app.route('/', methods=['GET', 'POST'])
def login():
    form = LoginForm()
    if form.validate_on_submit():
        user = User.query.filter_by(username=form.username.data).first()
        if user and bcrypt.check_password_hash(user.password, form.password.data):
            login_user(user)
            return redirect(url_for('dashboard'))
        else:
            flash("Invalid username or password", "danger")
    return render_template("login.html", form=form)

@app.route('/register', methods=['GET', 'POST'])
def register():
    form = RegisterForm()
    if form.validate_on_submit():
        hashed_password = bcrypt.generate_password_hash(form.password.data).decode('utf-8')
        new_user = User(username=form.username.data, password=hashed_password)
        db.session.add(new_user)
        db.session.commit()
        flash("Registration successful. Please login.", "success")
        return redirect(url_for('login'))
    return render_template("register.html", form=form)

@app.route('/logout')
@login_required
def logout():
    logout_user()
    return redirect(url_for('login'))


# CRUD OPERATIONS by SIDHARTH


@app.route('/dashboard')
@login_required
def dashboard():
    all_data = Data.query.all()
    return render_template("index.html", employees=all_data)

@app.route('/insert', methods=['POST'])
@login_required
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
@login_required
def update():
    my_data = Data.query.get(request.form['id'])
    my_data.name = request.form['name']
    my_data.student_class = int(request.form['student_class'])
    my_data.age = int(request.form['age'])
    my_data.email = request.form['email']

    db.session.commit()
    return redirect(url_for('dashboard'))

@app.route('/delete/<int:id>')
@login_required
def delete(id):
    my_data = Data.query.get(id)
    db.session.delete(my_data)
    db.session.commit()
    return redirect(url_for('dashboard'))

# CHART VISUALIZATION by SIDHARTH
@app.route('/chart')
@login_required
def chart():
    class_counts = [0,0,0,0,0]
    results = db.session.query(Data.student_class,db.func.count(Data.id)).group_by(Data.student_class).all()
    
    
    for student_class, count in results:
        if 1<=student_class <=5:
            class_counts[student_class-1]=count
    
    classes=["Class 1","Class 2","Class 3","Class 4","Class 5"]
    
    return render_template("chart.html",classes=classes,counts=class_counts)



if __name__ == "__main__":
    with app.app_context():
        db.create_all()
    app.run(debug=True)
