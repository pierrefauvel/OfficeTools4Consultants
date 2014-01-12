<?xml version="1.0" encoding="UTF-8" standalone="yes"?>

<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
xmlns:a="http://schemas.openxmlformats.org/drawingml/2006/main"
>

<xsl:template match="/o">
<p:sld 
	xmlns:a="http://schemas.openxmlformats.org/drawingml/2006/main"
	xmlns:r="http://schemas.openxmlformats.org/officeDocument/2006/relationships"
	xmlns:p="http://schemas.openxmlformats.org/presentationml/2006/main">
	<p:cSld>
		<p:spTree>
			<p:nvGrpSpPr>
				<p:cNvPr id="1" name="" />
				<p:cNvGrpSpPr />
				<p:nvPr />
			</p:nvGrpSpPr>
			<p:grpSpPr>
				<a:xfrm>
					<a:off x="0" y="0" />
					<a:ext cx="0" cy="0" />
					<a:chOff x="0" y="0" />
					<a:chExt cx="0" cy="0" />
				</a:xfrm>
			</p:grpSpPr>
			<p:sp>
				<p:nvSpPr>
					<p:cNvPr id="2" name="Titre 1" />
					<p:cNvSpPr>
						<a:spLocks noGrp="1" />
					</p:cNvSpPr>
					<p:nvPr>
						<p:ph type="title" />
					</p:nvPr>
				</p:nvSpPr>
				<p:spPr />
				<p:txBody>
					<a:bodyPr />
					<a:lstStyle />
					<a:p>
						<a:r>
							<a:rPr lang="fr-FR" dirty="0" smtClean="0" />
							<a:t><xsl:value-of select="./@text" /></a:t>
						</a:r>
					</a:p>
				</p:txBody>
			</p:sp>
			<p:sp>
				<p:nvSpPr>
					<p:cNvPr id="3" name="Espace réservé du contenu 2" />
					<p:cNvSpPr>
						<a:spLocks noGrp="1" />
					</p:cNvSpPr>
					<p:nvPr>
						<p:ph idx="1" />
					</p:nvPr>
				</p:nvSpPr>
				<p:spPr />
				<p:txBody>
					<a:bodyPr />
					<a:lstStyle />
					
					<xsl:if test="count(o)=0">
						<a:p />
					</xsl:if>
					<xsl:apply-templates select="o" />					
 				</p:txBody>
			</p:sp>
		</p:spTree>
	</p:cSld>
	<p:clrMapOvr>
		<a:masterClrMapping />
	</p:clrMapOvr>
</p:sld>

</xsl:template>

<xsl:template match="o" >
		<a:p>
		<a:pPr>
			<xsl:attribute name="lvl"><xsl:value-of select="@level"/></xsl:attribute>
		</a:pPr>
		<a:r>
			<a:rPr lang="fr-FR" dirty="0" smtClean="0" />
			<a:t><xsl:value-of select="./@text"/></a:t>
		</a:r>
	</a:p>
	<xsl:apply-templates select="./o" />
</xsl:template>

</xsl:stylesheet>