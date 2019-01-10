<#list xImpl>
	<note><title>Changes:</title>
	<#items as k, v>
		<#if k=="condition">
			<#list v as k1, v1>
				<#if k1=="condition">
					<#assign ocondition=v1>
				</#if>
				<#if k1=="current">
					<#assign condition=v1>
				</#if>
				<#if k1=="date">
					<#assign cdate=v1>
				</#if>
				<#if k1=="status">
					<#assign cstatus=v1>
				</#if>				
			</#list>
		</#if>
		<#if k=="description">
			<#assign description=v>
		</#if>
		<#if k=="old-description">
			<#assign oldDescription=v>
		</#if>		
		<#if k=="date">
			<#assign date=v>
		</#if>
		<#if k=="status">
			<#assign status=v>
		</#if>				
	</#items>
	<para>	
		<#if ocondition?? && condition??><para>Originally <emphasis>${ocondition}</emphasis> it was changed to <emphasis role="strong">${condition}</emphasis></para><para> <#if cstatus?? && cdate??><#if cstatus??><para>Status: <emphasis role="strong">${cstatus}</emphasis></#if> <#if cdate??>changed on ${cdate?date}</#if></#if></para> </#if>
		<#if description??><para>${md2docbook(description)}</para></#if>
		<#if oldDescription??><para><emphasis role="strong">Old description was:</emphasis></para><para>${md2docbook(oldDescription)}</para></#if>
		<#if status??><para>Status: <emphasis role="strong">${status}</emphasis> <#if date??>changed on ${date?date}</#if></para></#if>
	</para>
	</note>
</#list>
