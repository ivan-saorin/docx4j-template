<table>
    <thead>
        <tr>
            <th width="100px">Name</th>
            <th>Type</th>
        </tr>
        <tr>
            <th colspan=2>Description</th>
        </tr>        
    </thead>
    <tbody>
        <#list list as item>
        <tr class="${["odd", "even"][item.getIndex() % 2]}">
            <td><b>${item.name}</b></td>
            <td><i>${item.dataType}</i></td>
        </tr>
        <tr class="${["odd", "even"][item.getIndex() % 2]}">
            <td colspan=2>${item.description}</td>
        </tr>        
        </#list>
    </tbody>
</table>