<#list content as key, item>
	<para>${key}</para>
	<#include "./all_examples.ftl">	
	<#if item.schema??>
		<para><emphasis>Schema</emphasis>
		<programlisting>${item.schema}
	    <#if item.schema.xImplementation??>
	    	<#assign xImpl=item.schema.xImplementation>
	    	<#include "./ximplementation.ftl">
    	</#if>	
		</programlisting>
		</para>
    </#if>
</#list>