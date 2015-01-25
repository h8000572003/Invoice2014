package tw.com.wa.invoice.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Andy on 2015/1/20.
 */
public interface DBContract {

    enum Type {
        INT, STRING;

    }

    enum TableInfo {

        INVOICE_ENTER(Table.INVOICE_ENTER //
                , new TypeInfp("id", Type.INT)//
                , new TypeInfp("number", Type.STRING)//
                , new TypeInfp("inYm", Type.STRING)//
                , new TypeInfp("status", Type.STRING)//
                , new TypeInfp("award", Type.STRING)//


        );
        final static Map<Table, TableInfo> MAP;
        final TypeInfp[] typeInfp;
        final Table table;

        TableInfo(Table table, TypeInfp... typeInfp) {
            this.typeInfp = typeInfp;
            this.table = table;
        }

        static {
            Map<Table, TableInfo> map = new HashMap<>();
            for (TableInfo tableInfo : TableInfo.values()) {
                map.put(tableInfo.table, tableInfo);
            }
            MAP = Collections.unmodifiableMap(map);
        }


        public static TableInfo lookup(Table table) {
            return MAP.get(table);
        }

        public static TypeInfp[] lookColumns(Table table) {
            return MAP.get(table).typeInfp;
        }


    }

    enum Table {
        INVOICE_ENTER(invoice_enter.TABLE);
        final String name;


        Table(String name) {
            this.name = name;
        }
    }

    interface invoice_enter {
        String TABLE = "invoice_enter";

    }

    public class TypeInfp {
        private String name;
        private Type type;

        public TypeInfp(String name, Type type) {
            this.name = name;
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Type getType() {
            return type;
        }

        public void setType(Type type) {
            this.type = type;
        }
    }


}
