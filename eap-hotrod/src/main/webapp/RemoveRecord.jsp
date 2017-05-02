 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <h2>Welcome to EAP-JDG Demo</h2>


<br /> 
Add Data to the <i>default</i> Cache
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
           <input type="hidden" name="cacheAction" value="removeRecord">
           <input type="submit">
        </td>
        </tr>          
    
    </form>
</table>
