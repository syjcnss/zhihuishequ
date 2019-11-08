package com.ovu.lido.bean;

import java.util.List;

public class PaymentInfo {
    /**
     * timestamp : 20180509105727
     * errorCode : 0000
     * errorMsg : 正确返回
     * hash : null
     * token : null
     * data : {"listItem":[{"billList":[{"amount":0.4,"id":66,"begin_time":"2018-04-01","end_time":"2018-04-01","item_name":"水费","demurrageAmount":0,"charge_item_code":"66","house_code":"162209","record_id":3121740}],"sumAmountMany":0.4,"type_name":"水费类目","type_id":2},{"billList":[{"amount":0.87,"id":67,"begin_time":"2018-03-01","end_time":"2018-03-31","item_name":"物业管理费","demurrageAmount":0,"charge_item_code":"67","house_code":"162209","record_id":3121730}],"sumAmountMany":0.87,"type_name":"物业管理费8","type_id":1}],"sumAmount":1.27}
     * point : 0
     */

    private String timestamp;
    private String errorCode;
    private String errorMsg;
    private Object hash;
    private Object token;
    private DataBean data;
    private int point;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Object getHash() {
        return hash;
    }

    public void setHash(Object hash) {
        this.hash = hash;
    }

    public Object getToken() {
        return token;
    }

    public void setToken(Object token) {
        this.token = token;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public static class DataBean {
        /**
         * listItem : [{"billList":[{"amount":0.4,"id":66,"begin_time":"2018-04-01","end_time":"2018-04-01","item_name":"水费","demurrageAmount":0,"charge_item_code":"66","house_code":"162209","record_id":3121740}],"sumAmountMany":0.4,"type_name":"水费类目","type_id":2},{"billList":[{"amount":0.87,"id":67,"begin_time":"2018-03-01","end_time":"2018-03-31","item_name":"物业管理费","demurrageAmount":0,"charge_item_code":"67","house_code":"162209","record_id":3121730}],"sumAmountMany":0.87,"type_name":"物业管理费8","type_id":1}]
         * sumAmount : 1.27
         */

        private double sumAmount;
        private List<ListItemBean> listItem;

        public double getSumAmount() {
            return sumAmount;
        }

        public void setSumAmount(double sumAmount) {
            this.sumAmount = sumAmount;
        }

        public List<ListItemBean> getListItem() {
            return listItem;
        }

        public void setListItem(List<ListItemBean> listItem) {
            this.listItem = listItem;
        }

        public static class ListItemBean {
            /**
             * billList : [{"amount":0.4,"id":66,"begin_time":"2018-04-01","end_time":"2018-04-01","item_name":"水费","demurrageAmount":0,"charge_item_code":"66","house_code":"162209","record_id":3121740}]
             * sumAmountMany : 0.4
             * type_name : 水费类目
             * type_id : 2
             */

            private double sumAmountMany; // 单类型总价格
            private String type_name; // 缴费类型名称
            private int type_id;
            private List<BillListBean> billList; // 账单列表
            private boolean is_select;

            public boolean isIs_select() {
                return is_select;
            }

            public void setIs_select(boolean is_select) {
                this.is_select = is_select;
            }

            public double getSumAmountMany() {
                return sumAmountMany;
            }

            public void setSumAmountMany(double sumAmountMany) {
                this.sumAmountMany = sumAmountMany;
            }

            public String getType_name() {
                return type_name;
            }

            public void setType_name(String type_name) {
                this.type_name = type_name;
            }

            public int getType_id() {
                return type_id;
            }

            public void setType_id(int type_id) {
                this.type_id = type_id;
            }

            public List<BillListBean> getBillList() {
                return billList;
            }

            public void setBillList(List<BillListBean> billList) {
                this.billList = billList;
            }

            public static class BillListBean {
                /**
                 * amount : 0.4
                 * id : 66
                 * begin_time : 2018-04-01
                 * end_time : 2018-04-01
                 * item_name : 水费
                 * demurrageAmount : 0
                 * charge_item_code : 66
                 * house_code : 162209
                 * record_id : 3121740
                 */

                private double amount;
                private int id;
                private int num;
                private String begin_time;
                private String end_time;
                private String item_name;
                private int demurrageAmount;
                private String charge_item_code;
                private String house_code;
                private int record_id;
                private boolean is_select;

                public boolean isIs_select() {
                    return is_select;
                }

                public void setIs_select(boolean is_select) {
                    this.is_select = is_select;
                }

                public double getAmount() {
                    return amount;
                }

                public void setAmount(double amount) {
                    this.amount = amount;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public int getNum() {
                    return num;
                }

                public void setNum(int num) {
                    this.num = num;
                }

                public String getBegin_time() {
                    return begin_time;
                }

                public void setBegin_time(String begin_time) {
                    this.begin_time = begin_time;
                }

                public String getEnd_time() {
                    return end_time;
                }

                public void setEnd_time(String end_time) {
                    this.end_time = end_time;
                }

                public String getItem_name() {
                    return item_name;
                }

                public void setItem_name(String item_name) {
                    this.item_name = item_name;
                }

                public int getDemurrageAmount() {
                    return demurrageAmount;
                }

                public void setDemurrageAmount(int demurrageAmount) {
                    this.demurrageAmount = demurrageAmount;
                }

                public String getCharge_item_code() {
                    return charge_item_code;
                }

                public void setCharge_item_code(String charge_item_code) {
                    this.charge_item_code = charge_item_code;
                }

                public String getHouse_code() {
                    return house_code;
                }

                public void setHouse_code(String house_code) {
                    this.house_code = house_code;
                }

                public int getRecord_id() {
                    return record_id;
                }

                public void setRecord_id(int record_id) {
                    this.record_id = record_id;
                }
            }
        }
    }

//    private String id; //缴费类型id
//    private String groupName;// 缴费类型名称
//    private String groupDescribe; //缴费类型描述
//    private String sumAmountMany;// 单类型总价格
//    private boolean is_select;
//    private List<Order> billList;// 账单列表
//
//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//
//    public boolean isIs_select() {
//        return is_select;
//    }
//
//    public String getGroupName() {
//        return groupName;
//    }
//
//    public void setGroupName(String groupName) {
//        this.groupName = groupName;
//    }
//
//    public String getGroupDescribe() {
//        return groupDescribe;
//    }
//
//    public void setGroupDescribe(String groupDescribe) {
//        this.groupDescribe = groupDescribe;
//    }
//
//    public String getSumAmountMany() {
//        return sumAmountMany;
//    }
//
//    public void setSumAmountMany(String sumAmountMany) {
//        this.sumAmountMany = sumAmountMany;
//    }
//
//    public void setIs_select(boolean is_select) {
//        this.is_select = is_select;
//    }
//
//    public List<Order> getBillList() {
//        return billList;
//    }
//
//    public void setBillList(List<Order> billList) {
//        this.billList = billList;
//    }
//
//
//    public static class Order {
//        private String id;
//        private String childTime; //时间
//        private double amount; //总额
//        private boolean is_select; //是否选中
//
//        public String getId() {
//            return id;
//        }
//
//        public void setId(String id) {
//            this.id = id;
//        }
//
//        public String getChildTime() {
//            return childTime;
//        }
//
//        public void setChildTime(String childTime) {
//            this.childTime = childTime;
//        }
//
//        public double getAmount() {
//            return amount;
//        }
//
//        public void setAmount(double amount) {
//            this.amount = amount;
//        }
//
//        public boolean isIs_select() {
//            return is_select;
//        }
//
//        public void setIs_select(boolean is_select) {
//            this.is_select = is_select;
//        }
//    }
}
