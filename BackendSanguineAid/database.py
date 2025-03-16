from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker, declarative_base

# Definirea URL-ului bazei de date (folosim SQLite, dar poți schimba la PostgreSQL sau MySQL)
SQLALCHEMY_DATABASE_URL = "sqlite:///./users.db"

# Crearea engine-ului SQLAlchemy
engine = create_engine(SQLALCHEMY_DATABASE_URL, connect_args={"check_same_thread": False})

# Crearea sesiunii pentru interacțiunea cu baza de date
SessionLocal = sessionmaker(autocommit=False, autoflush=False, bind=engine)

# Crearea bazei de date
Base = declarative_base()
