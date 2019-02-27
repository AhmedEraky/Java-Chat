<!DOCTYPE html>
<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
 <xsl:template match="/">  
<html>
<head>

<style>
body {
  margin: 0 auto;
  max-width: 800px;
  padding: 0 20px;
}

.container {
  border: 2px solid #dedede;
  background-color: #f1f1f1;
  border-radius: 5px;
  padding: 10px;
  margin: 10px 0;
}

.darker {
  border-color: #ccc;
  background-color: #ddd;
}

.container::after {
  content: "";
  clear: both;
  display: table;
}


</style>
</head>
<body>

<xsl:for-each select="ChatSession/Message">
<div class="container darker">
 <h>
 
 <xsl:value-of select="Sender/firstName"/>
 <xsl:text> </xsl:text>
  <xsl:value-of select="Sender/lastName"/>
  
 </h>

  <p>
  
  <xsl:value-of select="MessageContent"/>
 
  </p>
 </div>
 </xsl:for-each>
 
</body>
</html>
 </xsl:template>
 </xsl:stylesheet>