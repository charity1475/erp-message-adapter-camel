<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:template match="/">
        <ERP_Transaction>
            <RefNo><xsl:value-of select="/transactionId"/></RefNo>
            <ClientDetails>
                <FullName><xsl:value-of select="/customer/name"/></FullName>
                <CustomerID><xsl:value-of select="/customer/id"/></CustomerID>
            </ClientDetails>
            <Financials>
                <TotalAmount><xsl:value-of select="/amount"/></TotalAmount>
                <CurrencyCode><xsl:value-of select="/currency"/></CurrencyCode>
                <PaymentMode><xsl:value-of select="/paymentMethod"/></PaymentMode>
            </Financials>
            <Timestamp><xsl:value-of select="/date"/></Timestamp>
        </ERP_Transaction>
    </xsl:template>
</xsl:stylesheet>
