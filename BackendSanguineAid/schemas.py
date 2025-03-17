from pydantic import BaseModel
from datetime import date

class UserBase(BaseModel):
    username: str
    email: str
    first_name: str
    last_name: str
    age: int
    gender: str
    date_of_birth: date
    city: str
    address: str
    hasdonatedbloodbefore: bool
    points: int

class UserCreate(UserBase):
    password: str

class UserLogin(BaseModel):
    username: str
    password: str

class UserResponse(UserBase):
    id: int
    class Config:
        orm_mode = True
