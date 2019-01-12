<table frame='all'><title>Statistics</title>
	<colspec colname='c1'/>
	<colspec colname='c2'/>
	<thead>
	<row>
	  <entry><emphasis role="strong">Counter</emphasis></entry>
	  <entry><emphasis role="strong">Value</emphasis></entry>
	</row>
	</thead>
	<tbody>	
	<row>
	  <entry><type>Total Paths</type></entry><entry><type>${model.stats.paths}</type></entry>
	</row>
	<row>
	  <entry><type>Total Operations</type></entry><entry><type>${model.stats.operations}</type></entry>
	</row>
	<row>	  
	  <entry><type>Total Get Operations</type></entry><entry><type>${model.stats.httpGetOperations}</type></entry>
	</row>
	<row>	  
	  <entry><type>Total Post Operations</type></entry><entry><type>${model.stats.httpPostOperations}</type></entry>
	</row>
	<row>
	  <entry><type>Total Put Operations</type></entry><entry><type>${model.stats.httpPutOperations}</type></entry>
	</row>
	<row>
	  <entry><type>Total Delete Operations</type></entry><entry><type>${model.stats.httpDeleteOperations}</type></entry>
	</row>
	<row>	  
	  <entry><type>Total Patch Operations</type></entry><entry><type>${model.stats.httpPatchOperations}</type></entry>
	</row>
	<row>	  
	  <entry><type>Avg Path Parameters per Opertion</type></entry><entry><type>${model.stats.avgPathParamsPerOperation?string["0.00"]}</type></entry>
	</row>
	<row>
	  <entry><type>Avg Req Header Parameters per Opertion</type></entry><entry><type>${model.stats.avgHeaderAttrsPerOperation?string["0.00"]}</type></entry>
	</row>
	<row>
	  <entry><type>Avg Query Parameters per Opertion</type></entry><entry><type>${model.stats.avgQueryParamsPerOperation?string["0.00"]}</type></entry>
	</row>
	<row>
	  <entry><type>Avg Examples per Opertion</type></entry><entry><type>${model.stats.avgExamplesPerOperation?string["0.00"]}</type></entry>
	</row>
	</tbody>
</table>			
