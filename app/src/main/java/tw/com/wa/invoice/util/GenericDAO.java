package tw.com.wa.invoice.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Andy on 2015/1/20.
 */
public abstract class GenericDAO<T> {

    private static final String TAG = "GenericDAO";
    private SQLiteDatabase db;
    private Context context;

    protected GenericDAO(SQLiteDatabase db, Context context) {
        this.db = db;
        this.context = context;
    }

    public abstract DBContract.Table getTable();

    public void insert(T insert) throws InvoiceDBException {

        this.insert(this.newInstanceContentValues(insert));
    }


    public void insert(ContentValues contentValues) throws InvoiceDBException {
        this.db.insert(this.getTable().name, null, contentValues);
    }

    public List<ContentValues> doQuery(String selection,
                                       String[] selectionArgs) throws InvoiceDBException {

        final DBContract.TypeInfp[] infps = DBContract.TableInfo.lookColumns(getTable());
        final Cursor cursor =
                db.query(this.getTable().name, null, selection, selectionArgs, "", "", "id");
        final List<ContentValues> valueses = new ArrayList<ContentValues>();
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {

            final ContentValues value = new ContentValues();
            valueses.add(value);

            for (DBContract.TypeInfp info : infps) {
                final String fileNmae = info.getName();
                switch (info.getType()) {

                    case INT:
                        value.put(fileNmae, cursor.getInt(cursor.getColumnIndex(fileNmae)));
                        break;

                    case STRING:
                        value.put(fileNmae, cursor.getString(cursor.getColumnIndex(fileNmae)));
                        break;
                }
            }
            cursor.moveToNext();
        }
        cursor.close();
        return valueses;
    }

    public List<T> doQuery(String selection,
                           String[] selectionArgs, Class<T> pClass) throws InvoiceDBException {

        final List<ContentValues> contentValueses = this.doQuery(selection, selectionArgs);

        final List<T> results = new ArrayList<>();
        for (ContentValues values : contentValueses) {
            results.add(this.newInstance(values, pClass));
        }
        return results;

    }

    private ContentValues newInstanceContentValues(T domina) {
        final Field[] fs = this.getAllFields(domina);

        ContentValues map = new ContentValues();
        for (Field f : fs) {
            f.setAccessible(true);

            try {
                if (f.getGenericType() == Integer.class) {

                    map.put(f.getName(), (int) f.get(domina));
                } else if (f.getGenericType() == String.class) {
                    map.put(f.getName(), (String) f.get(domina));
                }


            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            f.setAccessible(false);


        }
        return map;
    }

    private Field[] getAllFields(T obj) {
        try {

            return obj.getClass().getDeclaredFields();
        } catch (Exception e) {
            Log.e(TAG, "e=" + e.getMessage(), e);
            return new Field[]{};
        }
    }


    private T newInstance(ContentValues values, Class<T> pClass) {
        T newObj = null;

        try {
            newObj = pClass.newInstance();
            for (Map.Entry<String, Object> entry : values.valueSet()) {

                Field f = pClass.getDeclaredField(entry.getKey());
                f.setAccessible(true);
                f.set(newObj, entry.getValue());
                f.setAccessible(false);
            }
            for (String key : values.keySet()) {


            }
        } catch (Exception e) {
            Log.e(TAG, "e:" + e.getMessage());
        } finally {
            return newObj;
        }

    }
}
