package util;

import java.sql.ResultSet;

public class QueryBean extends SuperBean
{
    
    int pageSize = 10;
    int totalPage = 0;
    int currentPage = 1;
    int totalRecord = 0;
    int recordStart = 0;
    int recordEnd = 0;
            
    public int getPageSize() {
        return pageSize;
    }
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    /*public void setPageSize(String s) {
        try{
            this.pageSize = Integer.parseInt(s);
        }catch(Exception x){}        
        
    }*/
    public int getCurrentPage() {
        return currentPage;
    }
    public void setCurrentPage(int currentPageNumber) {
        this.currentPage = currentPageNumber;
    }
    
    /*protected void setCurrentPage(String s) {
        try{
            this.currentPage = Integer.parseInt(s);
        }catch(Exception x){}
    }*/
    
    public int getRecordEnd() {
        return recordEnd;
    }
    protected void setRecordEnd(int recordEnd) {
        this.recordEnd = recordEnd;
    }
    public int getRecordStart() {
        return recordStart;
    }
    protected void setRecordStart(int recordStart) {
        this.recordStart = recordStart;
    }
    public int getTotalPage() {
        return totalPage;
    }
    protected void setTotalPage(int totalPageNumber) {
        this.totalPage = totalPageNumber;
    }
    public int getTotalRecord() {
        return totalRecord;
    }
    protected void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
    }

    
    protected void processCurrentPageAttribute(ResultSet rs) throws Exception{
        rs.last();
        totalRecord = rs.getRow();
        rs.first();

        if (totalRecord <= 0) {
            recordStart = 0;
            recordEnd = 0;            
            return ;
        }

        if (pageSize > 0) {
            totalPage = (totalRecord - 1) / pageSize + 1;
            if (currentPage < 1)
                currentPage = 1;
            if (currentPage > totalPage)
                currentPage = totalPage;

            recordStart = pageSize * (currentPage - 1) + 1;
            recordEnd = Math.min(recordStart + pageSize - 1, totalRecord);
        } else {
            recordStart = 0;
            recordEnd = totalRecord;
        }
        rs.absolute(recordStart);     
    }
    
    String historyPage;
    String historyPageSize;

    public String getHistoryPage(){ return checkGet(historyPage); }
    public void setHistoryPage(String s){ historyPage=checkSet(s); }    
    public String getHistoryPageSize(){ return checkGet(historyPageSize); }
    public void setHistoryPageSize(String s){ historyPageSize=checkSet(s); }    
    
    

}
