package com.dnanexus.spark;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.hive.metastore.ObjectStore;
import org.apache.hadoop.hive.ql.session.SessionState;
import org.apache.hadoop.hive.shims.Utils;
import org.apache.hadoop.hive.metastore.api.Table;
import org.apache.hadoop.hive.metastore.api.MetaException;
import org.apache.hadoop.conf.Configuration;

import java.io.OutputStreamWriter;


public class CustomRawStore extends ObjectStore {

    static final private Log LOG = LogFactory.getLog("com.dnanexus.spark.CustomRawStore");

    public Table getTable(String dbName, String tableName) throws MetaException {
        printUserName("CustomRawStore****** " + dbName + "->" + tableName);
        Table tbl = super.getTable(dbName, tableName);
        if (tbl != null){
            LOG.info("****CustomRawStore Table Info " + tbl.toString());
            System.out.println("****CustomRawStore Table Info " + tbl.toString());
        }
        return tbl;
    }

    private void printUserName(String msg) {
        SessionState ss = SessionState.get();
        Configuration conf = getConf();
        String userName = null;

        try {
            userName = Utils.getUGI().getUserName();
            System.out.println("**** CustomRawStore  " + userName);
            System.out.println("**** UGI  " + Utils.getUGI().toString());
            LOG.info("**** CustomRawStore  " + userName);
            LOG.info("**** UGI  " + Utils.getUGI().toString());
            if (ss != null) {
                System.out.println(" ssUser " + ss.getUserName() + " AuthUser " +
                        ss.getUserFromAuthenticator() + " **** ");
                LOG.info(" ssUser " + ss.getUserName() + " AuthUser " +
                        ss.getUserFromAuthenticator() + " **** ");
            }
            if (conf != null) {
                //Configuration.dumpConfiguration(conf, new OutputStreamWriter(System.out));
            }
            System.out.println("   " + msg);
        } catch (Exception x) {
            System.out.println("**** Exception in printUserName");
            LOG.info("**** Exception in printUserName");
            x.printStackTrace();
        }
        if ("asdf".equals(userName)) throw new RuntimeException("Not Authorized - " + userName);
    }
}
