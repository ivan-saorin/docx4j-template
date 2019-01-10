<#list xImpl>
	<note><title>Changes:</title>
	<#items as k, v>
		<#if k=="original-condition">
			<#assign ocondition=v>
		</#if>
		<#if k=="condition">
			<#assign condition=v>
		</#if>
		<#if k=="description">
			<#assign description=v>
		</#if>
		<#if k=="date">
			<#assign date=v>
		</#if>
		<#if k=="status">
			<#assign status=v>
		</#if>				
	</#items>
	<para>	
	<#if ocondition?? && condition??><para>Originally <emphasis>${ocondition}</emphasis> it was changed to <emphasis role="strong">${condition}</emphasis></para> </#if>
	<#if description??><para>${md2docbook(description)}</para></#if>
	<#if status??><para>Status: <emphasis role="strong">${status}</emphasis> <#if date??>changed on ${date?date}</#if></para></#if>
	</para>
	</note>
</#list>
