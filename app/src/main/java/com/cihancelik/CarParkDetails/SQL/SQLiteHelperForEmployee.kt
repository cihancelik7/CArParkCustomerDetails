package com.cihancelik.CarParkDetails.SQL

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.cihancelik.CarParkDetails.employee_details.EmployeeModel

class SQLiteHelperForEmployee(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "employee.db"
        private const val TABLE_EMPLOYEE = "table_employee"
        private const val ID = "id"
        private const val NAME = "name"
        private const val LASTNAME = "lastname"
        private const val DEPARTMENT = "department"
        private const val EMAIL = "email"
        private const val ADDRESS = "address"
        private lateinit var cursor: Cursor

    }

    override fun onCreate(db: SQLiteDatabase?) {
        // Kullanıcılar Tablosu
        val createUserTable = """
    CREATE TABLE IF NOT EXISTS USERS (
        USER_ID INTEGER PRIMARY KEY,
        USER_NAME TEXT,
        PASSWORD TEXT,
        EMAIL_ADDRESS TEXT,
        START_DATE DATE,
        END_DATE DATE,
        UPDATE_DATE DATE,
        CREATION_DATE DATE,
        CREATED_BY INTEGER,
        FOREIGN KEY (CREATED_BY) REFERENCES USERS(USER_ID)
    )
""".trimIndent()

// Personel Bilgileri Tablosu
        val createHrEmployeesTable = """
    CREATE TABLE IF NOT EXISTS HR_EMPLOYEES (
        EMPLOYEE_ID INTEGER PRIMARY KEY,
        EMPLOYEE_NUMBER INTEGER,
        START_DATE DATE,
        END_DATE DATE,
        IS_ACTIVE TEXT,
        FIRST_NAME TEXT,
        LAST_NAME TEXT,
        BIRTH_DATE DATE,
        NATIONAL_ID INTEGER,
        MARITAL_STATUS TEXT,
        GENDER TEXT,
        ADDRESS_ID INTEGER,
        EMAIL_ADDRESS TEXT,
        FOREIGN KEY (ADDRESS_ID) REFERENCES ADDRESSES(ADDRESS_ID)
    )
""".trimIndent()

// Adresler Tablosu
        val createAddressesTable = """
    CREATE TABLE IF NOT EXISTS ADDRESSES (
        ADDRESS_ID INTEGER PRIMARY KEY,
        ADDRESS_NAME TEXT,
        START_DATE DATE,
        END_DATE DATE,
        COUNTRY TEXT,
        CITY TEXT,
        REGION TEXT,
        POSTAL_CODE TEXT,
        ADDRESS_LINE TEXT,
        UPDATE_DATE DATE,
        CREATION_DATE DATE,
        CREATED_BY INTEGER,
        FOREIGN KEY (CREATED_BY) REFERENCES USERS(USER_ID)
    )
""".trimIndent()

// İş Yeri (Lokasyon) Tablosu
        val createHrLocationsTable = """
    CREATE TABLE IF NOT EXISTS HR_LOCATIONS (
        LOCATION_ID INTEGER PRIMARY KEY,
        LOCATION_NAME TEXT,
        START_DATE DATE,
        END_DATE DATE,
        ADDRESS_ID INTEGER,
        NACE_KODU INTEGER,
        TEHLIKE_SINIFI TEXT,
        UPDATE_DATE DATE,
        CREATION_DATE DATE,
        CREATED_BY INTEGER,
        FOREIGN KEY (ADDRESS_ID) REFERENCES ADDRESSES(ADDRESS_ID),
        FOREIGN KEY (CREATED_BY) REFERENCES USERS(USER_ID)
    )
""".trimIndent()

        // Organizasyonlar Tablosu
        val createHrOrganizationsTable = """
    CREATE TABLE IF NOT EXISTS HR_ORGANIZATIONS (
        ORGANIZATION_ID INTEGER PRIMARY KEY,
        ORGANIZATION_NAME TEXT,
        START_DATE DATE,
        END_DATE DATE,
        PARENT_ORG_ID INTEGER,
        LOCATION_ID INTEGER,
        UPDATE_DATE DATE,
        CREATION_DATE DATE,
        CREATED_BY INTEGER,
        FOREIGN KEY (PARENT_ORG_ID) REFERENCES HR_ORGANIZATIONS(ORGANIZATION_ID),
        FOREIGN KEY (LOCATION_ID) REFERENCES HR_LOCATIONS(LOCATION_ID),
        FOREIGN KEY (CREATED_BY) REFERENCES USERS(USER_ID)
    )
""".trimIndent()

// İş Pozisyonları Tablosu
        val createHrJobsTable = """
    CREATE TABLE IF NOT EXISTS HR_JOBS (
        JOB_ID INTEGER PRIMARY KEY,
        JOB_NAME TEXT,
        START_DATE DATE,
        END_DATE DATE,
        UPDATE_DATE DATE,
        CREATION_DATE DATE
    )
""".trimIndent()

// Pozisyonlar Tablosu
        val createHrPositionsTable = """
    CREATE TABLE IF NOT EXISTS HR_POSITIONS (
        POSITION_ID INTEGER PRIMARY KEY,
        POSITION_NAME TEXT,
        START_DATE DATE,
        END_DATE DATE,
        JOB_ID INTEGER,
        UPDATE_DATE DATE,
        CREATION_DATE DATE,
        CREATED_BY INTEGER,
        FOREIGN KEY (JOB_ID) REFERENCES HR_JOBS(JOB_ID),
        FOREIGN KEY (CREATED_BY) REFERENCES USERS(USER_ID)
    )
""".trimIndent()

// Personel Atamaları Tablosu
        val createHrEmpAssignmentsTable = """
    CREATE TABLE IF NOT EXISTS HR_EMP_ASSIGNMENTS (
        ASSIGNMENT_ID INTEGER PRIMARY KEY,
        EMPLOYEE_ID INTEGER,
        POSITION_ID INTEGER,
        START_DATE DATE,
        END_DATE DATE,
        UPDATE_DATE DATE,
        CREATION_DATE DATE,
        FOREIGN KEY (EMPLOYEE_ID) REFERENCES HR_EMPLOYEES(EMPLOYEE_ID),
        FOREIGN KEY (POSITION_ID) REFERENCES HR_POSITIONS(POSITION_ID)
    )
""".trimIndent()

// Maaşlar Tablosu
        val createHrSalariesTable = """
    CREATE TABLE IF NOT EXISTS HR_SALARIES (
        SALARY_ID INTEGER PRIMARY KEY,
        EMPLOYEE_ID INTEGER,
        AMOUNT NUMBER(10, 2),
        START_DATE DATE,
        END_DATE DATE,
        UPDATE_DATE DATE,
        CREATION_DATE DATE,
        FOREIGN KEY (EMPLOYEE_ID) REFERENCES HR_EMPLOYEES(EMPLOYEE_ID)
    )
""".trimIndent()

        val createGlPeriods = """
            CREATE TABLE IF NOT EXISTS GL_PERIODS (
            PERIOD_ID INTEGER PRIMARY KEY,
            PERIOD_NAME TEXT,
            YEAR DATE,
            UPDATE_DATE DATE,
            CREATION_DATE DATE
    )
""".trimIndent()

        val createGlAccountCombinations = """
            CREATE TABLE IF NOT EXISTS GL_ACCOUNT_COMBINATIONS (
            GL_COD_COM_ID INTEGER PRIMARY KEY,
            SEGMENT1 TEXT,
            SEGMENT2 TEXT,
            SEGMENT3 TEXT,
            SEGMENT4 TEXT,
            SEGMENT5 TEXT,
            SEGMENT COMBINATION,
            UPDATE_DATE,
            CREATION_DATE
    )
""".trimIndent()

        val createGlJournals = """
            CREATE TABLE IF NOT EXISTS GL_JOURNALS (
            JOURNAL_ID INTEGER PRIMARY KEY,
            PERIOD_ID INTEGER,
            JOURNAL_DATE DATE,
            STATUS TEXT,
            AMOUNT NUMBER(10),
            UPDATE_DATE DATE,
            CREATION_DATE DATE,
            FOREIGN KEY (PERIOD_ID) REFERENCES GL_PERIODS(PERIOD_ID)
            )
""".trimIndent()

        val createGlJournalLines = """
            CREATE TABLE IF NOT EXISTS GL_JOURNAL_LINES (
            JOURNAL_LINE_ID INTEGER PRIMARY KEY,
            JOURNAL_ID INTEGER,
            JOURNAL_DATE DATE,
            GL_COD_COM_ID INTEGER,
            ACCOUNTED_CR_AMOUNT INTEGER,
            ACCOUNTED_DR_AMOUNT INTEGER,
            UPDATE_DATE DATE,
            CREATION_DATE DATE,
            FOREIGN KEY (JOURNAL_ID) REFERENCES GL_JOURNALS(JOURNAL_ID)
   )
""".trimIndent()

        val createPaySuppliers = """
            CREATE TABLE IF NOT EXISTS PAY_SUPPLIERS (
            SUPPLIER_ID INTEGER PRIMARY KEY,
            SUPPLIER_NUMBER INTEGER,
            START_DATE DATE,
            END_DATE,
            SUPPLIER_NAME TEXT,
            TCKN_VKN NUMBER(11),
            EMPL_SUPPLIER BOOLEAN,
            GLCCID NUMBER,
            ADDRESS_ID INTEGER,
            EMAIL_ADDRESS TEXT,
            UPDATE_DATE DATE,
            CREATION_DATE DATE,
            FOREIGN KEY (GLCCID) REFERENCES GL_ACCOUNT_COMBINATIONS(GL_COD_COM_ID)
   )
""".trimIndent()


        val createPayInvoices = """
            CREATE TABLE IF NOT EXISTS PAY_INVOICES (
            INVOICE_ID INTEGER PRIMARY KEY,
            SUPPLIER_ID INTEGER,
            INVOICE_DATE DATE,
            INVOICE_TYPE TEXT,
            INVOICE_AMOUNT INTEGER,
            TAX_AMOUNT INTEGER,
            CURRENCY TEXT,
            PAY_CHECK_METHOD_ID INTEGER,
            CHECK_TERM_ID INTEGER,
            UPDATE_DATE DATE,
            CREATION_DATE DATE,
            FOREIGN KEY (SUPPLIER_ID) REFERENCES PAY_SUPPLIERS(SUPPLIER_ID)
   )
""".trimIndent()

        val createPayInvoiceLines = """
            CREATE TABLE IF NOT EXISTS PAY_INVOICE_LINES (
            INVOICE_LINE_ID INTEGER PRIMARY KEY,
            INVOICE_ID INTEGER,
            ORDER_ID DATE,
            LINE_AMOUNT INTEGER,
            TAX_RATE TEXT,
            TAX_AMOUNT INTEGER,
            ITEM_ID INTEGER,
            GLCCID INTEGER,
            UPDATE_DATE DATE,
            CREATION_DATE DATE,
            FOREIGN KEY (INVOICE_ID) REFERENCES PAY_INVOICES(INVOICE_ID)
   )
""".trimIndent()
        val createPayCheckTerms = """
            CREATE TABLE IF NOT EXISTS PAY_CHECK_TERMS (
            CHECK_TERM_ID INTEGER PRIMARY KEY,
            TERM_NAME TEXT,
            UPDATE_DATE DATE,
            CREATION_DATE DATE
   )
""".trimIndent()


        val createPayCheckMethods = """
            CREATE TABLE IF NOT EXISTS PAY_CHECK_METHODS (
            CHECK_METHOD_ID INTEGER PRIMARY KEY,
             METHOD_NAME TEXT,
             TERM_ID INTEGER,
             UPDATE_DATE DATE,
             CREATION_DATE DATE,
             FOREIGN KEY(TERM_ID) REFERENCES PAY_CHECK_TERMS(TERM_ID)
   )
""".trimIndent()

        val createPayChecks = """
        CREATE TABLE IF NOT EXISTS PAY_CHECKS ( 
        CHECK_ID INTEGER PRIMARY KEY,
        CHECK_METHOD_ID INTEGER,
        CHECK_TERM_ID INTEGER,
        SUPPLIER_ID INTEGER,
        SUPPLIER_NUMBER INTEGER,
        SUPPLIER_NAME TEXT,
        CHECK_DATE DATE,
        CHECK_AMOUNT INTEGER,
        CHECK_ACCOUNT_ID INTEGER,
        UPDATE_DATE DATE,
        CREATION_DATE,
        FOREIGN KEY (CHECK_METHOD_ID) REFERENCES PAY_CHECK_METHODS(CHECK_METHOD_ID),
        FOREIGN KEY (CHECK_TERM_ID) REFERENCES PAY_CHECK_TERMS(CHECK_TERM_ID),
        FOREIGN KEY (SUPPLIER_ID) REFERENCES PAY_SUPPLIERS(SUPPLIER_ID),
        FOREIGN KEY (SUPPLIER_NUMBER) REFERENCES PAY_SUPPLIERS(SUPPLIER_NUMBER),
        FOREIGN KEY (SUPPLIER_NAME) REFERENCES PAY_SUPPLIERS(SUPPLIER_NAME),
        FOREIGN KEY (CHECK_ACCOUNT_ID) REFERENCES GL_ACCOUNT_COMBINATIONS(GL_COD_COM_ID)
    )            
""".trimIndent()




        db?.execSQL(createUserTable)
        db?.execSQL(createHrEmployeesTable)
        db?.execSQL(createAddressesTable)
        db?.execSQL(createHrLocationsTable)
        db?.execSQL(createHrOrganizationsTable)
        db?.execSQL(createHrJobsTable)
        db?.execSQL(createHrPositionsTable)
        db?.execSQL(createHrEmpAssignmentsTable)
        db?.execSQL(createHrSalariesTable)
        db?.execSQL(createHrSalariesTable)
        db?.execSQL(createGlPeriods)
        db?.execSQL(createGlAccountCombinations)
        db?.execSQL(createGlJournals)
        db?.execSQL(createGlJournalLines)
        db?.execSQL(createPaySuppliers)
        db?.execSQL(createPayInvoices)
        db?.execSQL(createPayInvoiceLines)
        db?.execSQL(createPayCheckTerms)
        db?.execSQL(createPayCheckMethods)
        db?.execSQL(createPayChecks)


    }


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 2) {
            db?.execSQL("ALTER TABLE $TABLE_EMPLOYEE ADD COLUMN $LASTNAME TEXT")
        }
    }

    fun insertEmployee(employee: EmployeeModel): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(NAME, employee.name)
        contentValues.put(LASTNAME, employee.lastName)
        contentValues.put(DEPARTMENT, employee.department)
        contentValues.put(EMAIL, employee.email)
        contentValues.put(ADDRESS, employee.address)

        val success = db.insert(TABLE_EMPLOYEE, null, contentValues)
        db.close()
        return success
    }

    fun getAllEmployee(): ArrayList<EmployeeModel> {
        val employeeList: ArrayList<EmployeeModel> = ArrayList()
        val selectQuery = "SELECT * FROM $TABLE_EMPLOYEE"
        val db = this.readableDatabase
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            e.printStackTrace()
            // Hata ele alınır
            return ArrayList()
        }






        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: Exception) {
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var id: Int
        var name: String
        var lastname: String
        var department: String
        var email: String
        var address: String



        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex("id"))
                name = cursor.getString(cursor.getColumnIndex("name"))
                lastname = cursor.getString(cursor.getColumnIndex("lastname"))
                department = cursor.getString(cursor.getColumnIndex("department"))
                email = cursor.getString(cursor.getColumnIndex("email"))
                address = cursor.getString(cursor.getColumnIndex("address"))
                val emp = EmployeeModel(
                    id = id,
                    name = name,
                    lastName = lastname,
                    department = department,
                    email = email,
                    address = address
                )
                employeeList.add(emp)
            } while (cursor.moveToNext())
        }
        return employeeList
    }

    fun updateEmployee(emp: EmployeeModel): Int {
        val db = this.writableDatabase
        val contextValues = ContentValues()
        contextValues.put(ID, emp.id)
        contextValues.put(NAME, emp.name)
        contextValues.put(LASTNAME, emp.lastName)
        contextValues.put(DEPARTMENT, emp.department)
        contextValues.put(EMAIL, emp.email)
        contextValues.put(ADDRESS, emp.address)

        val success = db.update(TABLE_EMPLOYEE, contextValues, "id=" + emp.id, null)
        db.close()
        return success
    }

    fun deleteEmployeeById(id: Int): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID, id)

        val success = db.delete(TABLE_EMPLOYEE, "id=$id", null)
        db.close()
        return success
    }

}