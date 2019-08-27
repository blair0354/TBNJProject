<%@ page contentType="text/html;charset=utf-8"%>
<script language="javascript">
var pageListener = new Object();
pageListener.beforeGotoPage = function(){
    // do nothing,for override.
}

function gotoPage(iPageNo)
{
    pageListener.beforeGotoPage();
    if (isObj(form1.state)) form1.state.value = "queryAll";
	form1.currentPage.value = iPageNo;
	form1.submit();
}
</script>
<%
   try{
   	util.QueryBean queryBean = (util.QueryBean)request.getAttribute("QueryBean");
   	int pageSize = queryBean.getPageSize();
   	int totalPage = queryBean.getTotalPage();
   	int currentPage = queryBean.getCurrentPage();
   	int totalRecord = queryBean.getTotalRecord();
   	int recordStart = queryBean.getRecordStart();
   	int recordEnd = queryBean.getRecordEnd();
   	String strField = "<input type='hidden' name='pageSize' value='" + pageSize + "'><input type='hidden' name='currentPage' value='1'>";
    if((pageSize<=0) || (totalRecord<=0) || (totalPage<=1)) {
    	out.println(strField);
    	return;
    }
%>
	<input type="hidden" name="currentPageSize" value="<%=pageSize%>">
	<input type="hidden" name="currentPage" value="<%=currentPage%>"> 
    <table width="99%" border="0" align="center">
      <tr>
        <td class="querytext" valign="middle"><span class="pagetext" >一頁<input type="text" class="field_Q" name="pageSize" maxlength="4" size="4" value="<%=pageSize%>" style="font-size: 12px;">筆, 共<%=totalRecord%>筆 第<%=recordStart%>到<%=recordEnd%>筆</span></td>
        
        <td class="querytext" valign="middle">
        <div align="right"><span class="pagetext" >
        			  共<%=totalPage%>頁</span>
				   [ <a class="pagetext" href="javascript: gotoPage(1)">第一頁</a>
				   <%
					for(int i=0;i<totalPage;i++)
					{
						if(i==currentPage-1)
						{
							out.println("<span class=\"pagetext\" style=\"color:red\">" + Integer.toString(i+1) + "</span>");

						}
						else if((currentPage-6<=i) && (i<=currentPage+5))
						{
							out.println("<a class=\"pagetext\" href=\"javascript:javascript: gotoPage("+(i+1)+")\">" + (i+1)+"</a>");
						}

					}
    
				%>
				<a class="pagetext" href="javascript: gotoPage(<%=totalPage%>)">末頁</a> ][
<%				
                if(currentPage>1){
%>
                <a class="pagetext" href="javascript: gotoPage(<%=currentPage-1%>)">上一頁</a>
<%
                }
				if(currentPage<totalPage){
%>
                <a class="pagetext" href="javascript: gotoPage(<%=currentPage+1%>)">下一頁</a>
<%			    
				}  
%>        		
                ]
	 	</div>
	 	</td>
      </tr>
    </table>
<%
    }catch(Exception x){
        x.printStackTrace();
    }

%>
