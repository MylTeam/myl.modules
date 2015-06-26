package com.myl.messages;

import java.util.List;

public class ConnectionInfoMessage {

    private final ConnectionInfo connectionInfo;

    public ConnectionInfoMessage(String user, List<String> activeUsers, List<String> formats) {
        this.connectionInfo = new ConnectionInfo(user, activeUsers, formats);
    }

    public ConnectionInfo getConnectionInfo() {
        return connectionInfo;
    }

    class ConnectionInfo {

        private final String user;

        private final List<String> activeUsers;
        private final List<String> formats;

        private ConnectionInfo(String user, List<String> activeUsers, List<String> formats) {
            this.user = user;
            this.activeUsers = activeUsers;
            this.formats = formats;
        }

        public String getUser() {
            return user;
        }

        public List<String> getActiveUsers() {
            return activeUsers;
        }
        public List<String> getFormats() {
            return formats;
        }
        
    }

}
