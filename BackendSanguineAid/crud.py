from sqlalchemy.orm import Session
import models, schemas
from passlib.context import CryptContext

pwd_context = CryptContext(schemes=["bcrypt"], deprecated="auto")

def get_user_by_username(db: Session, username: str):
    return db.query(models.User).filter(models.User.username == username).first()

def create_user(db: Session, user: schemas.UserCreate):
    hashed_password = pwd_context.hash(user.password)
    db_user = models.User(
        username=user.username,
        password=hashed_password,
        email=user.email,
        first_name=user.first_name,
        last_name=user.last_name,
        age=user.age,
        gender=user.gender,
        date_of_birth=user.date_of_birth,
        city=user.city,
        address=user.address,
        hasdonatedbloodbefore=user.hasdonatedbloodbefore,
        points=0,
    )
    db.add(db_user)
    db.commit()
    db.refresh(db_user)
    return db_user

def authenticate_user(db: Session, username: str, password: str):
    user = get_user_by_username(db, username)
    if not user or not pwd_context.verify(password, user.password):
        return None
    return user

def add_points_to_user(db: Session, username: str, points: int = 5):
    user = db.query(models.User).filter(models.User.username == username).first()
    if not user:
        return None
    user.points += points
    db.commit()
    return user