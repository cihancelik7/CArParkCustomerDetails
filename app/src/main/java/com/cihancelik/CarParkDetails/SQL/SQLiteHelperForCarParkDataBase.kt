package com.cihancelik.CarParkDetails.SQL

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

open class SQLiteHelperForCarParkDataBase(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "carpark.db"
        private const val TABLE_CARPARKDATABASE = "table_carparkdatabase"
        private lateinit var cursor: Cursor

    }

    override fun onCreate(db: SQLiteDatabase?) {
        // Kullanıcılar Tablosu
        val createUserTable = """
    CREATE TABLE IF NOT EXISTS USERS (
        USER_ID INTEGER PRIMARY KEY AUTOINCREMENT ,
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
        EMPLOYEE_ID INTEGER PRIMARY KEY AUTOINCREMENT ,
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
        ADDRESS_ID INTEGER PRIMARY KEY AUTOINCREMENT ,
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
        FOREIGN KEY (CREATED_BY) REFERENCES USERS(USER_ID)
    )
""".trimIndent()

// İş Yeri (Lokasyon) Tablosu
        val createHrLocationsTable = """
    CREATE TABLE IF NOT EXISTS HR_LOCATIONS (
        LOCATION_ID INTEGER PRIMARY KEY AUTOINCREMENT ,
        LOCATION_NAME TEXT,
        START_DATE DATE,
        END_DATE DATE,
        ADDRESS_ID INTEGER,
        NACE_KODU INTEGER,
        TEHLIKE_SINIFI TEXT,
        UPDATE_DATE DATE,
        CREATION_DATE DATE,
        FOREIGN KEY (ADDRESS_ID) REFERENCES ADDRESSES(ADDRESS_ID),
        FOREIGN KEY (CREATED_BY) REFERENCES USERS(USER_ID)
    )
""".trimIndent()

        // Organizasyonlar Tablosu
        val createHrOrganizationsTable = """
    CREATE TABLE IF NOT EXISTS HR_ORGANIZATIONS (
        ORGANIZATION_ID INTEGER PRIMARY KEY AUTOINCREMENT ,
        ORGANIZATION_NAME TEXT,
        START_DATE DATE,
        END_DATE DATE,
        PARENT_ORG_ID INTEGER,
        LOCATION_ID INTEGER,
        UPDATE_DATE DATE,
        CREATION_DATE DATE,
        FOREIGN KEY (PARENT_ORG_ID) REFERENCES HR_ORGANIZATIONS(ORGANIZATION_ID),
        FOREIGN KEY (LOCATION_ID) REFERENCES HR_LOCATIONS(LOCATION_ID),
        FOREIGN KEY (CREATED_BY) REFERENCES USERS(USER_ID)
    )
""".trimIndent()

// İş Pozisyonları Tablosu
        val createHrJobsTable = """
    CREATE TABLE IF NOT EXISTS HR_JOBS (
        JOB_ID INTEGER PRIMARY KEY AUTOINCREMENT ,
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
        POSITION_ID INTEGER PRIMARY KEY AUTOINCREMENT ,
        POSITION_NAME TEXT,
        START_DATE DATE,
        END_DATE DATE,
        JOB_ID INTEGER,
        UPDATE_DATE DATE,
        CREATION_DATE DATE,
        FOREIGN KEY (JOB_ID) REFERENCES HR_JOBS(JOB_ID),
        FOREIGN KEY (CREATED_BY) REFERENCES USERS(USER_ID)
    )
""".trimIndent()

// Personel Atamaları Tablosu
        val createHrEmpAssignmentsTable = """
    CREATE TABLE IF NOT EXISTS HR_EMP_ASSIGNMENTS (
        ASSIGNMENT_ID INTEGER PRIMARY KEY AUTOINCREMENT ,
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
        SALARY_ID INTEGER PRIMARY KEY AUTOINCREMENT ,
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
            PERIOD_ID INTEGER PRIMARY KEY AUTOINCREMENT ,
            PERIOD_NAME TEXT,
            YEAR DATE,
            UPDATE_DATE DATE,
            CREATION_DATE DATE
    )
""".trimIndent()

        val createGlAccountCombinations = """
            CREATE TABLE IF NOT EXISTS GL_ACCOUNT_COMBINATIONS (
            GL_COD_COM_ID INTEGER PRIMARY KEY AUTOINCREMENT ,
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
            JOURNAL_ID INTEGER PRIMARY KEY AUTOINCREMENT ,
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
            JOURNAL_LINE_ID INTEGER PRIMARY KEY AUTOINCREMENT ,
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
            SUPPLIER_ID INTEGER PRIMARY KEY AUTOINCREMENT ,
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
            INVOICE_ID INTEGER PRIMARY KEY AUTOINCREMENT ,
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
            INVOICE_LINE_ID INTEGER PRIMARY KEY AUTOINCREMENT ,
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
            CHECK_TERM_ID INTEGER PRIMARY KEY AUTOINCREMENT ,
            TERM_NAME TEXT,
            UPDATE_DATE DATE,
            CREATION_DATE DATE
   )
""".trimIndent()


        val createPayCheckMethods = """
            CREATE TABLE IF NOT EXISTS PAY_CHECK_METHODS (
            CHECK_METHOD_ID INTEGER PRIMARY KEY AUTOINCREMENT ,
             METHOD_NAME TEXT,
             TERM_ID INTEGER,
             UPDATE_DATE DATE,
             CREATION_DATE DATE,
             FOREIGN KEY(TERM_ID) REFERENCES PAY_CHECK_TERMS(TERM_ID)
   )
""".trimIndent()

        val createPayChecks = """
        CREATE TABLE IF NOT EXISTS PAY_CHECKS ( 
        CHECK_ID INTEGER PRIMARY KEY AUTOINCREMENT ,
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
        val createPurchaseOrders = """
            CREATE TABLE IF NOT EXISTS PURCHASE_ORDERS (
            ORDER_ID INTEGER PRIMARY KEY AUTOINCREMENT ,
            ORDER_CODE TEXT,
            ITEM_ID INTEGER,
            ITEM_UNIT TEXT,
            ITEM_AMOUNT TEXT,
            UPDATE_DATE DATE,
            CREATION_DATE DATE,
            FOREIGN KEY (ITEM_ID) REFERENCES ITEMS(ITEM_ID) 
            )
            """.trimIndent()

        val createItems = """
            CREATE TABLE IF NOT EXISTS ITEMS (
            ITEM_ID INTEGER PRIMARY KEY AUTOINCREMENT ,
            ITEM_CODE TEXT,
            ITEM_DESCRIPTION TEXT,
            UPDATE_DATE DATE,
            CREATION_DATE DATE
            )
            """.trimIndent()

        val createRecCustomers = """
            CREATE TABLE IF NOT EXISTS REC_CUSTOMERS (
            CUSTOMER_ID INTEGER PRIMARY KEY AUTOINCREMENT ,
            CUSTOMER_NUMBER INTEGER,
            CUSTOMER_NAME TEXT,
            START_DATE DATE,
            END_DATE DATE,
            TCKN NUMBER(11),
            EMP_CUSTOMER BOOLEAN,
            GLCCID INTEGER,
            ADDRESS_ID INTEGER,
            EMAIL_ADDRESS TEXT,
            UPDATE_DATE DATE,
            CREATION_DATE
            )
            """.trimIndent()

        val createRecInvoices = """
            CREATE TABLE IF NOT EXISTS REC_INVOICES (
            REC_INVOICE_ID INTEGER PRIMARY KEY AUTOINCREMENT ,
            CUSTOMER_ID INTEGER,
            INVOICE_DATE DATE,
            INVOICE_TYPE TEXT,
            INVOICE_AMOUNT INTEGER,
            TAX_AMOUNT INTEGER,
            CURRENCY TEXT,
            RECEIPT_METHOD_ID INTEGER,
            RECEIPT_TERM_ID INTEGER,
            UPDATE_DATE DATE,
            CREATION_DATE DATE,
            FOREIGN KEY (CUSTOMER_ID) REFERENCES REC_CUSTOMERS(CUSTOMER_ID),
            FOREIGN KEY (CURRENCY) REFERENCES PAY_INVOICES(CURRENCY),
            FOREIGN KEY (RECEIPT_METHOD_ID) REFERENCES REC_RECEIPT_METHOD(RECEIPT_METHOD_ID),
            FOREIGN KEY (RECEIPT_TERM_ID) REFERENCES REC_RECEIPT_TERMS(RECEIPT_TERM_ID)
            )
            """.trimIndent()

        val createRecInvoiceLines = """
            CREATE TABLE IF NOT EXISTS REC_INVOICE_LINES (
            REC_INVOICE_LINE_ID INTEGER PRIMARY KEY AUTOINCREMENT ,
            REC_INVOICE_ID INTEGER,
            LINE_AMOUNT INTEGER,
            TAX_RATE TEXT,
            TAX_AMOUNT INTEGER,
            GLCCID INTEGER,
            UPDATE_DATE DATE,
            CREATION_DATE DATE,
            FOREIGN KEY (REC_INVOICE_ID) REFERENCES PAY_INVOICES(INVOICE_ID),
            FOREIGN KEY (GLCCID) REFERENCES GL_ACCOUNT_COMBINATIONS(GL_COD_COM_ID)
            )
            """.trimIndent()
        val createRecReceiptTerms = """
            CREATE TABLE IF NOT EXISTS REC_RECEIPT_TERMS (
            RECEIPT_TERM_ID INTEGER PRIMARY KEY AUTOINCREMENT ,
            TERM_NAME TEXT,
            UPDATE_DATE DATE,
            CREATION_DATE DATE
            )
            """.trimIndent()

        val createRecReceiptMethods = """
            CREATE TABLE IF NOT EXISTS REC_RECEIPT_METHODS (
            RECEIPT_METHOD_ID INTEGER PRIMARY KEY AUTOINCREMENT ,
            METHOD_NAME TEXT,
            RECEIPT_TERM_ID INTEGER,
            UPDATE_DATE DATE,
            CREATION_DATE DATE,
            FOREIGN KEY(RECEIPT_TERM_ID) REFERENCES REC_RECEIPT_TERMS(RECEIPT_TERM_ID)
            )
            """.trimIndent()

        val createRecReceipts = """
        CREATE TABLE IF NOT EXISTS REC_RECEIPTS (
        RECEIPT_ID INTEGER PRIMARY KEY AUTOINCREMENT ,
        RECEIPT_METHOD_ID INTEGER,
        RECEIPT_TERM_ID INTEGER,
        CUSTOMER_ID INTEGER,
        CUSTOMER_NUMBER INTEGER,
        CUSTOMER_NAME TEXT,
        RECEIPT_DATE DATE,
        RECEIPT_AMOUNT INTEGER,
        CHECK_ACCOUNT_ID INTEGER,
        UPDATE_DATE DATE,
        CREATION_DATE DATE,
        FOREIGN KEY (RECEIPT_METHOD_ID) REFERENCES REC_RECEIPT_METHODS(RECEIPT_METHOD_ID),
        FOREIGN KEY (RECEIPT_TERM_ID) REFERENCES REC_RECEIPT_TERM(RECEIPT_TERM_ID),
        FOREIGN KEY (CUSTOMER_ID) REFERENCES REC_CUSTOMERS(CUSTOMER_ID),
        FOREIGN KEY (CUSTOMER_NUMBER) REFERENCES REC_CUSTOMERS(CUSTOMER_NUMBER),
        FOREIGN KEY (CUSTOMER_NAME) REFERENCES REC_CUSTOMERS(CUSTOMER_NAME),
        FOREIGN KEY (CHECK_ACCOUNT_ID) REFERENCES GL_ACCOUNT_COMBINATIONS(GL_COD_COM_ID)
        )
        """.trimIndent()
        val createCrmCustCarInfo = """
            CREATE TABLE IF NOT EXISTS CRM_CUST_CAR_INFO (
            CAR_INFO_ID INTEGER PRIMARY KEY AUTOINCREMENT ,
            PLATE_NUMBER TEXT,
            MODEL TEXT,
            CUSTOMER_ID INTEGER,
            UPDATE_DATE DATE,
            CREATION_DATE DATE
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
        db?.execSQL(createPurchaseOrders)
        db?.execSQL(createItems)
        db?.execSQL(createRecCustomers)
        db?.execSQL(createRecInvoices)
        db?.execSQL(createRecInvoiceLines)
        db?.execSQL(createRecReceiptTerms)
        db?.execSQL(createRecReceiptMethods)
        db?.execSQL(createRecReceipts)
        db?.execSQL(createCrmCustCarInfo)



    }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
                TODO("Not yet implemented")
        }




}