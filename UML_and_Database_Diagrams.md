# Patient Record System Diagrams

## 1. Class Diagram (UML)
This diagram illustrates the Model-View-Controller/Service architecture of the Patient Record System.

```mermaid
classDiagram
    class Main {
        +main(String[] args)
    }

    class LoginFrame {
        -String DEFAULT_USERNAME
        -String DEFAULT_PASSWORD
        +LoginFrame()
    }

    class Dashboard {
        +Dashboard()
    }

    class AddPatientForm {
        +AddPatientForm()
    }

    class ViewPatients {
        +ViewPatients()
    }

    class DBConnection {
        -String URL
        -String USER
        -String PASSWORD
        +getConnection() Connection
    }

    class PatientService {
        +patientIdExists(int patientId) boolean
        +addPatient(Integer patientId, String name, int age, String gender, String phone, String disease) boolean
        +deletePatientById(int id) boolean
    }

    Main --> LoginFrame : Launches
    LoginFrame --> Dashboard : On successful login
    Dashboard --> AddPatientForm : Opens
    Dashboard --> ViewPatients : Opens
    AddPatientForm --> PatientService : Calls for adding
    ViewPatients --> PatientService : Calls for fetching/deleting
    PatientService --> DBConnection : Gets connection
```

## 2. Database Entity-Relationship Diagram
This ER diagram shows the tables and relationships in the database used by the system.

```mermaid
erDiagram
    Patient {
        int id PK "Auto-incrementing Patient ID"
        varchar name "Patient Name"
        int age "Patient Age"
        varchar gender "Gender"
        varchar phone "Phone Number"
        varchar disease "Primary Disease/Symptoms"
    }
```
