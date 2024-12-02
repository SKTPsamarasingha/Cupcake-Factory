package com.example.cupcake.Helpers;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.cupcake.Model.Admin;
import com.example.cupcake.Model.Order;
import com.example.cupcake.Model.SelectedItems;
import com.example.cupcake.Model.User;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(@Nullable Context context) {
        super(context, "CupcakeDB", null, 1);
    }


    private static final String CREATE_USER_TABLE = "CREATE TABLE user (" + "userID INTEGER PRIMARY KEY AUTOINCREMENT," + "username TEXT NOT NULL," + " number INTEGER NOT NULL," + " address TEXT NOT NULL," + " password TEXT NOT NULL)";
    private static final String CREATE_ADMIN_TABLE = "CREATE TABLE admin (" + " adminID INTEGER PRIMARY KEY AUTOINCREMENT," + " username TEXT NOT NULL," + " password TEXT NOT NULL)";
    private static final String CREATE_OFFER_TABLE = "CREATE TABLE offer (" + "offerID INTEGER PRIMARY KEY AUTOINCREMENT," + " offerName TEXT," + "discount INTEGER," + "isActive INTEGER)";
    private static final String CREATE_CATEGORIES_TABLE = "CREATE TABLE Categorie (" + "CategoryID INTEGER PRIMARY KEY AUTOINCREMENT," + "offerID INTEGER," + " name TEXT," + "info TEXT," + " FOREIGN KEY(offerID) REFERENCES offer(offerID))";
    private static final String CREATE_CUPCAKE_TABLE = "CREATE TABLE cupcake (" + "cupcakeID INTEGER PRIMARY KEY AUTOINCREMENT," + "CategoryID INTEGER," + "name TEXT ," + " price NUMERIC," + "info TEXT," + "img BLOB," + " FOREIGN KEY(CategoryID) REFERENCES Categorie(CategoryID))";
    private static final String CREATE_ORDER_TABLE = "CREATE TABLE orders (" + " orderID INTEGER PRIMARY KEY AUTOINCREMENT," + "userID INTEGER NOT NULL," + "orderDate TEXT," + "totalPrice NUMERIC NOT NULL," + "orderStatus INTEGER," + " FOREIGN KEY(userID) REFERENCES user(userID))";
    private static final String CREATE_ORDERED_ITEMS_TABLE = "CREATE TABLE orderedItem (" + "cupcakeID INTEGER," + "  orderID INTEGER," + "quntity INTEGER," + "FOREIGN KEY(cupcakeID) REFERENCES cupcake(cupcakeID)," + "FOREIGN KEY(orderID) REFERENCES orders(orderID))";


    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL(CREATE_USER_TABLE);
        DB.execSQL(CREATE_ADMIN_TABLE);
        DB.execSQL(CREATE_OFFER_TABLE);
        DB.execSQL(CREATE_CATEGORIES_TABLE);
        DB.execSQL(CREATE_CUPCAKE_TABLE);
        DB.execSQL(CREATE_ORDER_TABLE);
        DB.execSQL(CREATE_ORDERED_ITEMS_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    public Cursor viewData(String SQLQuery) {
        SQLiteDatabase DB = this.getReadableDatabase();
        return DB.rawQuery(SQLQuery, null);
    }

    public static boolean insertData(Context context, ContentValues contentValues, String table) {
        try (DBHelper DBClass = new DBHelper(context)) {
            SQLiteDatabase DB = DBClass.getWritableDatabase();
            long result = DB.insert(table, null, contentValues);
            return result != -1;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateData(Context context, ContentValues values, String table, String whereClause, int id) {
        try (DBHelper DBClass = new DBHelper(context);
             SQLiteDatabase DB = DBClass.getWritableDatabase()) {

            String[] whereArgs = {String.valueOf(id)};
            int rowsAffected = DB.update(table, values, whereClause, whereArgs);

            Log.d("DatabaseUpdate", "Table: " + table + ", WhereClause: " + whereClause + ", ID: " + id + ", RowsAffected: " + rowsAffected);
            Log.d("DatabaseUpdate", "Values: " + values.toString());

            return rowsAffected > 0;
        } catch (Exception e) {
            Log.e("DatabaseUpdate", "Error updating data", e);
            return false;
        }
    }

    public static boolean deleteData(Context context, String table, String whereClause, int id) {
        try (DBHelper DBClass = new DBHelper(context);
             SQLiteDatabase DB = DBClass.getWritableDatabase()) {

            String[] whereArgs = {String.valueOf(id)};
            int rowsAffected = DB.delete(table, whereClause, whereArgs);

            Log.d("DatabaseDelete", "Table: " + table + ", WhereClause: " + whereClause + ", ID: " + id + ", RowsAffected: " + rowsAffected);

            return rowsAffected > 0;
        } catch (Exception e) {
            Log.e("DatabaseDelete", "Error deleting data", e);
            return false;
        }
    }

    public static User getUser(Context context, String username, String password) {
        String table = "user";
        String[] columns = {"userID", "username", "number", "address", "password"};
        String selection = "username = ? AND password = ?";
        String[] selectionArgs = {username, password};

        try (DBHelper dbHelper = new DBHelper(context);
             SQLiteDatabase db = dbHelper.getReadableDatabase();
             Cursor cursor = db.query(table, columns, selection, selectionArgs, null, null, null)) {

            if (cursor != null && cursor.moveToFirst()) {
                int userID = cursor.getInt(cursor.getColumnIndexOrThrow("userID"));
                String fetchedUsername = cursor.getString(cursor.getColumnIndexOrThrow("username"));
                int number = cursor.getInt(cursor.getColumnIndexOrThrow("number"));
                String address = cursor.getString(cursor.getColumnIndexOrThrow("address"));
                String fetchedPassword = cursor.getString(cursor.getColumnIndexOrThrow("password"));

                return new User(userID, fetchedUsername, number, address, fetchedPassword);
            }
        } catch (Exception e) {
            Log.e("DBError", "Error retrieving user", e);
        }
        return null;
    }

    public static Admin getAdmin(Context context, String username, String password) {
        String table = "admin";
        String[] columns = {"adminID", "username", "password"};
        String selection = "username = ? AND password = ?";
        String[] selectionArgs = {username, password};

        try (DBHelper dbHelper = new DBHelper(context);
             SQLiteDatabase db = dbHelper.getReadableDatabase();
             Cursor cursor = db.query(table, columns, selection, selectionArgs, null, null, null)) {

            if (cursor != null && cursor.moveToFirst()) {
                int adminID = cursor.getInt(cursor.getColumnIndexOrThrow("adminID"));
                String fetchedUsername = cursor.getString(cursor.getColumnIndexOrThrow("username"));
                String fetchedPassword = cursor.getString(cursor.getColumnIndexOrThrow("password"));

                return new Admin(adminID, fetchedUsername, fetchedPassword);
            }
        } catch (Exception e) {
            Log.e("DBError", "Error retrieving admin", e);
        }
        return null;
    }

    public static boolean insertOrderWithItems(Context context, int userID, String orderDate, double totalPrice, int orderStatus, ArrayList<SelectedItems> items) {
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues orderValues = new ContentValues();
            orderValues.put("userID", userID);
            orderValues.put("orderDate", orderDate);
            orderValues.put("totalPrice", totalPrice);
            orderValues.put("orderStatus", orderStatus);

            long orderID = db.insert("orders", null, orderValues);

            if (orderID == -1) {
                return false;
            }

            for (SelectedItems item : items) {
                ContentValues itemValues = new ContentValues();
                itemValues.put("cupcakeID", item.getCupcake().getCupcakeID());
                itemValues.put("orderID", orderID);
                itemValues.put("quntity", item.getAmount());

                long result = db.insert("orderedItem", null, itemValues);
                if (result == -1) {
                    return false;
                }
            }

            db.setTransactionSuccessful();
            return true;
        } finally {
            db.endTransaction();
            db.close();
        }
    }


    public static ArrayList<Order> getOrdersByStatus(Context context, int status) {
        ArrayList<Order> orderList = new ArrayList<>();
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT " +
                "    o.orderID, " +
                "    u.username, " +
                "    o.orderDate, " +
                "    o.totalPrice, " +
                "    o.orderStatus, " +
                "    c.name, " +
                "    oi.quntity, " +
                "    (c.price * oi.quntity) AS subtotal " +
                "FROM " +
                "    orders o " +
                "JOIN " +
                "    user u ON o.userID = u.userID " +
                "JOIN " +
                "    orderedItem oi ON o.orderID = oi.orderID " +
                "JOIN " +
                "    cupcake c ON oi.cupcakeID = c.cupcakeID " +
                "WHERE " +
                "    o.orderStatus = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(status)});

        int currentOrderID = -1;
        Order currentOrder = null;
        ArrayList<String[]> currentOrderData = null;

        if (cursor.moveToFirst()) {
            do {
                int orderID = cursor.getInt(cursor.getColumnIndexOrThrow("orderID"));

                if (orderID != currentOrderID) {
                    // If this is a new order, add the previous order to the list (if exists)
                    if (currentOrder != null) {
                        currentOrder.setOrderData(currentOrderData.toArray(new String[0][]));
                        orderList.add(currentOrder);
                    }

                    // Start a new order
                    String username = cursor.getString(cursor.getColumnIndexOrThrow("username"));
                    String orderDate = cursor.getString(cursor.getColumnIndexOrThrow("orderDate"));
                    String totalPrice = cursor.getString(cursor.getColumnIndexOrThrow("totalPrice"));
                    String orderStatus = cursor.getString(cursor.getColumnIndexOrThrow("orderStatus"));

                    currentOrderID = orderID;
                    currentOrderData = new ArrayList<>();
                    currentOrder = new Order(username, orderID, orderDate, totalPrice, orderStatus, null);
                }

                // Add order item data
                String cupcakeName = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String quantity = cursor.getString(cursor.getColumnIndexOrThrow("quntity"));
                String subtotal = cursor.getString(cursor.getColumnIndexOrThrow("subtotal"));
                currentOrderData.add(new String[]{cupcakeName, quantity, subtotal});

            } while (cursor.moveToNext());

            // Add the last order
            if (currentOrder != null) {
                currentOrder.setOrderData(currentOrderData.toArray(new String[0][]));
                orderList.add(currentOrder);
            }
        }

        cursor.close();
        db.close();
        return orderList;
    }

    public static ArrayList<Order> getOrdersByUserID(Context context, int userid,int status) {
        ArrayList<Order> orderList = new ArrayList<>();
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT " +
                "    o.orderID, " +
                "    u.username, " +
                "    o.orderDate, " +
                "    o.totalPrice, " +
                "    o.orderStatus, " +
                "    c.name, " +
                "    oi.quntity, " +
                "    (c.price * oi.quntity) AS subtotal " +
                "FROM " +
                "    orders o " +
                "JOIN " +
                "    user u ON o.userID = u.userID " +
                "JOIN " +
                "    orderedItem oi ON o.orderID = oi.orderID " +
                "JOIN " +
                "    cupcake c ON oi.cupcakeID = c.cupcakeID " +
                "WHERE " +
                "    u.userID = ?  AND  o.orderStatus = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userid),String.valueOf(status)});

        int currentOrderID = -1;
        Order currentOrder = null;
        ArrayList<String[]> currentOrderData = null;

        if (cursor.moveToFirst()) {
            do {
                int orderID = cursor.getInt(cursor.getColumnIndexOrThrow("orderID"));

                if (orderID != currentOrderID) {
                    // If this is a new order, add the previous order to the list (if exists)
                    if (currentOrder != null) {
                        currentOrder.setOrderData(currentOrderData.toArray(new String[0][]));
                        orderList.add(currentOrder);
                    }

                    // Start a new order
                    String username = cursor.getString(cursor.getColumnIndexOrThrow("username"));
                    String orderDate = cursor.getString(cursor.getColumnIndexOrThrow("orderDate"));
                    String totalPrice = cursor.getString(cursor.getColumnIndexOrThrow("totalPrice"));
                    String orderStatus = cursor.getString(cursor.getColumnIndexOrThrow("orderStatus"));

                    currentOrderID = orderID;
                    currentOrderData = new ArrayList<>();
                    currentOrder = new Order(username, orderID, orderDate, totalPrice, orderStatus, null);
                }

                // Add order item data
                String cupcakeName = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String quantity = cursor.getString(cursor.getColumnIndexOrThrow("quntity"));
                String subtotal = cursor.getString(cursor.getColumnIndexOrThrow("subtotal"));
                currentOrderData.add(new String[]{cupcakeName, quantity, subtotal});

            } while (cursor.moveToNext());

            // Add the last order
            if (currentOrder != null) {
                currentOrder.setOrderData(currentOrderData.toArray(new String[0][]));
                orderList.add(currentOrder);
            }
        }

        cursor.close();
        db.close();
        return orderList;
    }
    public static boolean acceptOrder(Context context, int id) {
        try (DBHelper DBClass = new DBHelper(context);
             SQLiteDatabase DB = DBClass.getWritableDatabase()) {


            String query = "UPDATE orders SET orderStatus = 1 WHERE orderID = ?";

            // Execute the update statement
            DB.execSQL(query, new Object[]{id});

            return true;
        } catch (Exception e) {
            Log.e("DatabaseUpdated", "Error updating Order status", e);
            return false;
        }
    }

    public static boolean deleteOrder(Context context, int id) {
        try (DBHelper DBClass = new DBHelper(context);
             SQLiteDatabase DB = DBClass.getWritableDatabase()) {

            String deleteOrderItem = "DELETE FROM orderedItem WHERE orderID = ?";
            String deleteOrder = "DELETE FROM orders WHERE orderID = ?";

            // Execute the delete statements
            DB.execSQL(deleteOrderItem, new Object[]{id});
            DB.execSQL(deleteOrder, new Object[]{id});

            return true;
        } catch (Exception e) {
            Log.e("DatabaseDelete", "Error deleting order", e);
            return false;
        }
    }


    @SuppressLint("Range")
    public static byte[] getImage(Context context, int cupcakeID) {
        DBHelper DBClass = new DBHelper(context);
        SQLiteDatabase DB = DBClass.getWritableDatabase();
        byte[] image = null;

        String query = "SELECT " + "img" + " FROM " + "cupcake" +
                " WHERE " + "cupcakeID" + " = ?";

        Cursor cursor = DB.rawQuery(query, new String[]{String.valueOf(cupcakeID)});

        if (cursor != null && cursor.moveToFirst()) {
            image = cursor.getBlob(cursor.getColumnIndex("img"));
            cursor.close();
        }

        return image;
    }

}




//    public boolean updateData(Context context, ContentValues values, String table, int id) {
//        try (DBHelper DBClass = new DBHelper(context);
//             SQLiteDatabase DB = DBClass.getWritableDatabase()) {
//
//            String whereClause = "offerID = ?";
//            String[] whereArgs = {String.valueOf(id)};
//
//            int rowsAffected = DB.update(table, values, whereClause, whereArgs);
//
//            return rowsAffected > 0;
//        } catch (Exception e) {
//            // Log the exception or handle it appropriately
//            e.printStackTrace();
//            return false;
//        }
//    }


//    public static boolean isExist(Context context, String table, String whereClause, int value) {
//        DBHelper dbHelper = new DBHelper(context);
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        boolean exists = false;
//        Cursor cursor = null;
//
//        try {
//            String query = "SELECT COUNT(*) FROM " + table + " WHERE " + whereClause;
//            String[] selectionArgs = {String.valueOf(value)};
//
//            cursor = db.rawQuery(query, selectionArgs);
//            if (cursor != null && cursor.moveToFirst()) {
//                int count = cursor.getInt(0);
//                exists = (count > 0);
//            }
//
//            Log.d("DBHelper", "isExist query: " + query);
//            Log.d("DBHelper", "isExist args: " + value);
//            Log.d("DBHelper", "isExist result: " + exists);
//
//        } catch (Exception e) {
//            Log.e("DBHelper", "Error in isExist: " + e.getMessage());
//            e.printStackTrace();
//        } finally {
//            if (cursor != null) {
//                cursor.close();
//            }
//            db.close();
//            dbHelper.close();
//        }
//
//        return exists;
//    }
//
//    public boolean insertUserData(String userName, String number, String address, String password) {
//        SQLiteDatabase DB = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//
//        contentValues.put("username", userName);
//        contentValues.put("number", number);
//        contentValues.put("address", address);
//        contentValues.put("password", password);
//        long result = DB.insert("user", null, contentValues);
//        DB.close();
//
//        return result != -1;
//    }
//
//    public boolean insertAdminData(String username, String password) {
//        SQLiteDatabase DB = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("username", username);
//        contentValues.put("password", password);
//        long result = DB.insert("admin", null, contentValues);
//        DB.close();
//        return result != -1;
//    }
//
//    public boolean insertOfferData(String name, int discount, boolean isActive) {
//        SQLiteDatabase DB = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//
//        contentValues.put("offerName", name);
//        contentValues.put("discount", discount);
//        contentValues.put("isActive", isActive ? 1 : 0);
//        long result = DB.insert("offer", null, contentValues);
//        DB.close();
//        return result != -1;
//    }
//

//
//    public boolean insertCupcakeData(int CategoryID, String name, int price, String info) {
//        SQLiteDatabase DB = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("CategoryID", CategoryID);
//        contentValues.put("name", name);
//        contentValues.put("price", price);
//        contentValues.put("info", info);
//        long result = DB.insert("cupcake", null, contentValues);
//        DB.close();
//        return result != -1;
//    }
//
//    public boolean insertOrdersData(int userID, String orderDate, int totalPrice, boolean orderStatus) {
//        SQLiteDatabase DB = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("userID", userID);
//        contentValues.put("orderDate", orderDate);
//        contentValues.put("totalPrice", totalPrice);
//        contentValues.put("orderStatus", orderStatus);
//        long result = DB.insert("orders", null, contentValues);
//        DB.close();
//        return result != -1;
//    }
//
//    public boolean insertOrderedItemData(int cupcakeID, int orderID, int quantity) {
//        SQLiteDatabase DB = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("cupcakeID", cupcakeID);
//        contentValues.put("orderID", orderID);
//        contentValues.put("quantity", quantity);
//
//        long result = DB.insert("orderedItem ", null, contentValues);
//        DB.close();
//        return result != -1;
//    }


//}


// test data
//                boolean a = DB.insertAdminData("admin", "123");
//                boolean u = DB.insertUserData("dex","123","123colombo","12312adasd");
//                boolean of = DB.insertOfferData("mothers day",50,true);
//                boolean cat = DB.insertCategorieData(1,"Mothers dat","blueberry cupcake");
//                boolean cup = DB.insertCupcakeData(1,"CheeseCake cupcake",550,"new york style");
//                boolean ord = DB.insertOrdersData(1,"2024-5-15",5500,true);
//                boolean itm = DB.insertOrderedItemData(1,1,10);

