package database_helper

import sun.reflect.generics.reflectiveObjects.NotImplementedException
import java.sql.DriverManager
import java.sql.SQLException
import java.sql.Statement

class DatabaseHelper : IDatabaseHelper {

    private var statement: Statement? = null

    override fun connect(DBName: String) {
        try {
            Class.forName("org.postgresql.Driver")
            this.statement = DriverManager
                    //                    .getConnection("jdbc:sqlite:D:/programs/sqlite/" + DBName + ".db")
                    .getConnection("jdbc:postgresql://localhost:5432/" + DBName, "postgres", "admin")
                    .createStatement()
        } catch (e: Exception) {
            System.err.println(e.message)
        }

    }

    override fun createTable(tableName: String, vararg fieldsAndRestrics: String): Int {
        if (statement != null) {
            var request = "create table if not exists $tableName ("

            for (i in fieldsAndRestrics.indices) {
                if (i != fieldsAndRestrics.size - 1)
                    request = request + (fieldsAndRestrics[i] + ", ")
                else
                    request = request + (fieldsAndRestrics[i] + ");")
            }
            try {
                statement!!.executeUpdate(request)
            } catch (e: SQLException) {
                System.err.println(e.message)
            }

        }
        return NULL_STATEMENT_CODE
    }

    override fun dropTable(tableName: String): Int {
        if (statement != null) {
            try {
                statement!!.executeUpdate("drop table if exists $tableName cascade;")
            } catch (e: SQLException) {
                System.err.println(e.message)
            }

        }
        return NULL_STATEMENT_CODE
    }

    override fun addConstraint(restriction: String): Int {
        //        if(statement!=null)
        //            try {
        //        return statement.executeUpdate(restriction);
        //    }
        //            catch (SQLException e){
        //        System.err.println(e.getMessage());
        //    }
        //            return NULL_STATEMENT_CODE;
        throw NotImplementedException()
    }

    override fun insertValuesInto(tableName: String, vars: Array<String>, values: Array<String>): Boolean {
        if (statement != null) {
            var request = "insert into $tableName("
            for (i in vars.indices) {
                if (i != vars.size - 1)
                    request += (vars[i] + ", ")
                else
                    request += (vars[i] + ")")
            }
            request += " values ("

            for (i in values.indices) {
                if (i != values.size - 1)
                    request += ("'" + values[i] + "', ")
                else
                    request += ("'" + values[i] + "'")
            }

            request += ")"

            try {
                return statement!!.execute(request)
            } catch (e: SQLException) {
                System.err.println(e.message)
            }

        }
        return false
    }

    companion object {

        val NULL_STATEMENT_CODE = 10001
    }
}

interface IDatabaseHelper {
    fun connect(DBName: String)
    fun createTable(tableName: String, vararg fieldsAndRestrics: String): Int
    fun dropTable(tableName: String): Int
    fun addConstraint(constraint: String): Int
    fun insertValuesInto(tableName: String, vars: Array<String>, values: Array<String>): Boolean
}
