 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <h2>Welcome to EAP-JDG Demo</h2>


<br /> 
Find Data from the <i>default</i> Cache
<br />

<table>
    <form method="post" action="cache">
        <tr>
        <td>Cache Key</td>
        <td>
            <input type="text" name="cacheKey">
        </td>
        </tr>
        
                <tr>
       
        <td>
           <input type="hidden" name="cacheAction" value="findRecord">
           <input type="submit">
        </td>
        </tr>          
    
    </form>
</table>
