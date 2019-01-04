<#list schema>
<table frame='all'>
<colspec colname='c1'/>
<colspec colname='c2'/>
<thead>
<row>
  <entry><emphasis role="strong">Field name / Ref</emphasis></entry>
  <entry><emphasis role="strong">Data type</emphasis></entry>
</row>
</thead>
<tbody>
<#items as key, item>
<row>
  <#if key == "+" || key == "or" || key == "and">
  <entry namest="c1" nameend="c2" align="left"><emphasis>${key}</emphasis></entry>
  <#else> 
  <entry>${key}</entry>
  <entry>${item}</entry>
  </#if>
</row>
</#items>
</tbody>
</table>
</#list>