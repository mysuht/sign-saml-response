package com.example.test;

public class AppConstants {
	public static final String XML_CONTENT = """
			<samlp:Response xmlns:samlp="urn:oasis:names:tc:SAML:2.0:protocol" xmlns:saml="urn:oasis:names:tc:SAML:2.0:assertion" ID="_8e8dc5f69a98cc4c1ff3427e5ce34606fd672f91e6" Version="2.0" IssueInstant="2014-07-17T01:01:48Z" Destination="http://sp.example.com/demo1/index.php?acs" InResponseTo="ONELOGIN_4fee3b046395c4e751011e97f8900b5273d56685">
  <saml:Issuer>http://idp.example.com/metadata.php</saml:Issuer>
  <samlp:Status>
    <samlp:StatusCode Value="urn:oasis:names:tc:SAML:2.0:status:Success"/>
  </samlp:Status>
  <saml:Assertion xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xs="http://www.w3.org/2001/XMLSchema" ID="pfxe68cdb80-0064-dd6e-0935-0db523c76cc8" Version="2.0" IssueInstant="2014-07-17T01:01:48Z">
    <saml:Issuer>http://idp.example.com/metadata.php</saml:Issuer><ds:Signature xmlns:ds="http://www.w3.org/2000/09/xmldsig#">
  <ds:SignedInfo><ds:CanonicalizationMethod Algorithm="http://www.w3.org/2001/10/xml-exc-c14n#"/>  
    <ds:SignatureMethod Algorithm="http://www.w3.org/2000/09/xmldsig#rsa-sha1"/>
  <ds:Reference URI="#pfxe68cdb80-0064-dd6e-0935-0db523c76cc8"><ds:Transforms><ds:Transform Algorithm="http://www.w3.org/2000/09/xmldsig#enveloped-signature"/><ds:Transform Algorithm="http://www.w3.org/2001/10/xml-exc-c14n#"/></ds:Transforms><ds:DigestMethod Algorithm="http://www.w3.org/2000/09/xmldsig#sha1"/><ds:DigestValue>ggdTj3A5OgHfjjjGr1XKFubMLHg=</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>hD988Pm3HkTXPNSmXOIs2w7RG0bHLhtVTDkGHBaS6iPly2jR7i8zHwBE/FNFldWekijpcKfVt0ZZgG7t1hZgv6piR27kSGQnoOZfd+F0HMgckYOkcU6jp2ikdBJEjss9chRBfNRlpEKJFw5nCtInYMezeYRbKPap2b659L4ad9w=</ds:SignatureValue>
<ds:KeyInfo><ds:X509Data><ds:X509Certificate>MIICajCCAdOgAwIBAgIBADANBgkqhkiG9w0BAQ0FADBSMQswCQYDVQQGEwJ1czETMBEGA1UECAwKQ2FsaWZvcm5pYTEVMBMGA1UECgwMT25lbG9naW4gSW5jMRcwFQYDVQQDDA5zcC5leGFtcGxlLmNvbTAeFw0xNDA3MTcxNDEyNTZaFw0xNTA3MTcxNDEyNTZaMFIxCzAJBgNVBAYTAnVzMRMwEQYDVQQIDApDYWxpZm9ybmlhMRUwEwYDVQQKDAxPbmVsb2dpbiBJbmMxFzAVBgNVBAMMDnNwLmV4YW1wbGUuY29tMIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDZx+ON4IUoIWxgukTb1tOiX3bMYzYQiwWPUNMp+Fq82xoNogso2bykZG0yiJm5o8zv/sd6pGouayMgkx/2FSOdc36T0jGbCHuRSbtia0PEzNIRtmViMrt3AeoWBidRXmZsxCNLwgIV6dn2WpuE5Az0bHgpZnQxTKFek0BMKU/d8wIDAQABo1AwTjAdBgNVHQ4EFgQUGHxYqZYyX7cTxKVODVgZwSTdCnwwHwYDVR0jBBgwFoAUGHxYqZYyX7cTxKVODVgZwSTdCnwwDAYDVR0TBAUwAwEB/zANBgkqhkiG9w0BAQ0FAAOBgQByFOl+hMFICbd3DJfnp2Rgd/dqttsZG/tyhILWvErbio/DEe98mXpowhTkC04ENprOyXi7ZbUqiicF89uAGyt1oqgTUCD1VsLahqIcmrzgumNyTwLGWo17WDAa1/usDhetWAMhgzF/Cnf5ek0nK00m0YZGyc4LzgD0CROMASTWNg==</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature>
    <saml:Subject>
      <saml:NameID SPNameQualifier="http://sp.example.com/demo1/metadata.php" Format="urn:oasis:names:tc:SAML:2.0:nameid-format:transient">_ce3d2948b4cf20146dee0a0b3dd6f69b6cf86f62d7</saml:NameID>
      <saml:SubjectConfirmation Method="urn:oasis:names:tc:SAML:2.0:cm:bearer">
        <saml:SubjectConfirmationData NotOnOrAfter="2024-01-18T06:21:48Z" Recipient="http://sp.example.com/demo1/index.php?acs" InResponseTo="ONELOGIN_4fee3b046395c4e751011e97f8900b5273d56685"/>
      </saml:SubjectConfirmation>
    </saml:Subject>
    <saml:Conditions NotBefore="2014-07-17T01:01:18Z" NotOnOrAfter="2024-01-18T06:21:48Z">
      <saml:AudienceRestriction>
        <saml:Audience>http://sp.example.com/demo1/metadata.php</saml:Audience>
      </saml:AudienceRestriction>
    </saml:Conditions>
    <saml:AuthnStatement AuthnInstant="2014-07-17T01:01:48Z" SessionNotOnOrAfter="2024-07-17T09:01:48Z" SessionIndex="_be9967abd904ddcae3c0eb4189adbe3f71e327cf93">
      <saml:AuthnContext>
        <saml:AuthnContextClassRef>urn:oasis:names:tc:SAML:2.0:ac:classes:Password</saml:AuthnContextClassRef>
      </saml:AuthnContext>
    </saml:AuthnStatement>
    <saml:AttributeStatement>
      <saml:Attribute Name="uid" NameFormat="urn:oasis:names:tc:SAML:2.0:attrname-format:basic">
        <saml:AttributeValue xsi:type="xs:string">test</saml:AttributeValue>
      </saml:Attribute>
      <saml:Attribute Name="mail" NameFormat="urn:oasis:names:tc:SAML:2.0:attrname-format:basic">
        <saml:AttributeValue xsi:type="xs:string">test@example.com</saml:AttributeValue>
      </saml:Attribute>
      <saml:Attribute Name="eduPersonAffiliation" NameFormat="urn:oasis:names:tc:SAML:2.0:attrname-format:basic">
        <saml:AttributeValue xsi:type="xs:string">users</saml:AttributeValue>
        <saml:AttributeValue xsi:type="xs:string">examplerole1</saml:AttributeValue>
      </saml:Attribute>
    </saml:AttributeStatement>
  </saml:Assertion>
</samlp:Response>
			""";
	
	
	public static final String PRIVATE_KEY = """
			-----BEGIN RSA PRIVATE KEY-----
MIIEpAIBAAKCAQEA1AvuyYtdXju0fVEs4lzI4KDUirNA+FoZwkv5g64jWByrR51B
rEfsjubFsblpGURCH38r3opqPnkccOJv4cDySNzdQIsUnRbZD6v4qOlSA7P1jMh6
pcSr0f7j6tZbxR84IDQZjSvPIo72MsphxSvYJpXVY6frhIi1H7n4217pZwflHWKO
4oOeEr5jBrWZHth2Deo7/SdHu/Nz1QqOoYLRAo7Kf3Ym+Tu8KvqZYtDi5zysR7+l
GGVa3Z40rqGNXAKvhtCpYS74oYMq7ewbiFyyWD8b8aXrDKZtKiErlrXHIZiVM7iK
6X8Et4HFGvKuKB0i5OW5f9stt3ow9Z6kopZubwIDAQABAoIBABVcZl+zRGKbzSDa
rpTXF/1Y9lBKon7iRLoIAUi8oB7eNHrwYo9CZ5g1szCmJuo+sBKQMxEmDHHirXTJ
us+ZKk8gwMEhwZ/Zj2A+yLvrVTG+TTwzbfeJCm4LKVac5X3EGVlVJkXMZf9rO23+
aT79nba1XVu+rMEbLHPXvjQLKaQRPo6PEAlndNx0FGscE1KnZVQULb0NUBXn5eHv
nM8on6Y4pJ4HbfZwsuJaS8B8IXtdqKROtXedRlJjlnNRbbstleB+T0VgmT0Xx9n6
Ox/hfBluIIGdfUcntodUfzecruQxhkBl/R1qX/fOyH9OMx5mzYGnBTfd5wqVv9mG
iYziPXECgYEA8ougzm7Jb4JSAN/p6Yd41wUZhPfZ2RAjpR9UtLoN6XkbgXZW4YIU
RYk31G6xMTDF1mlFr/11IVEAA8c0KF+pRKUr2LzBBslvUkctnJ2jKO6xT46BS5ua
WBDJMyPdn+9xT5J95G9n1+fJpdC2he4Pty51n7GA4bzu6nKebbHQU50CgYEA388x
Z2dqhoMMQyl9psyznGsD++q9JBieQoBzl7jep4YG1bB6ZURU72rit4G9IGY7HrEi
LVFtyM0lGXK3QtbXKpZpL7vHGx0jFmRvCh5tsQGMLBjFRsY9G5KsU32gwuMIsvtC
CWEPmUzIKelEhC4wog8jIpzUt3J8meiUTDZYqnsCgYBD9K8XZ570qTTEnffxwE0x
IXKm+LKGyKjUCNU41bA0LARy3280O7fmS63izSjEOYAUtOYbiw4YSZORe2y3qyXH
3+dLEIecmS+hg+PZr6kZPNddDPZY8r3p0roso+3ic7nxq3+uUzBrI7wxwyqAl9S7
ZD78gUU0+jSOZ5LP9TQDWQKBgQDFQtYVgKX4ZtrQ+2nfMT8Bl6UMTaplfTlH/fVj
/bJpTw0JHSHTzbVazAeFnFk4jEKBW7h23vV3bbDMTDrQM5Gz3JxbURM9h6HcY6sm
7QjbYTTNzQtbxPApNE6ky+58tyR8tE5Q+/aGtJEbt5kHM7rzq4nZQQePpN3DvaWv
wprFrwKBgQCqRKfrnSLosrXtZyauzX3T0GE0zisVsNGCPEvVejQ2XbJO8u9ZYrm7
KvC1M8AgSJcO8Pk0UjpJQrGWiXVDjZ8BVqth8FF0ouICy6aLLT9rRT+FWk/93Xn5
2pXiewjr1GiqR6eoY7mR502ngbZyZDZKozZWRPbe32etRmEVIpOGdg==
-----END RSA PRIVATE KEY-----
			""";
	
public static final String CERTIFICATE = """
		-----BEGIN CERTIFICATE-----
MIIDIjCCAgoCCQDhO5cZKZTI8TANBgkqhkiG9w0BAQsFADBTMQswCQYDVQQGEwJT
QTEPMA0GA1UECAwGSmVkZGFoMQ8wDQYDVQQHDAZqZWRkYWgxETAPBgNVBAoMCG9h
c2lzc3lzMQ8wDQYDVQQLDAZCYWxzYW0wHhcNMjQwMTE2MTgwODQwWhcNMjYwNzA0
MTgwODQwWjBTMQswCQYDVQQGEwJTQTEPMA0GA1UECAwGSmVkZGFoMQ8wDQYDVQQH
DAZqZWRkYWgxETAPBgNVBAoMCG9hc2lzc3lzMQ8wDQYDVQQLDAZCYWxzYW0wggEi
MA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQDUC+7Ji11eO7R9USziXMjgoNSK
s0D4WhnCS/mDriNYHKtHnUGsR+yO5sWxuWkZREIffyveimo+eRxw4m/hwPJI3N1A
ixSdFtkPq/io6VIDs/WMyHqlxKvR/uPq1lvFHzggNBmNK88ijvYyymHFK9gmldVj
p+uEiLUfufjbXulnB+UdYo7ig54SvmMGtZke2HYN6jv9J0e783PVCo6hgtECjsp/
dib5O7wq+pli0OLnPKxHv6UYZVrdnjSuoY1cAq+G0KlhLvihgyrt7BuIXLJYPxvx
pesMpm0qISuWtcchmJUzuIrpfwS3gcUa8q4oHSLk5bl/2y23ejD1nqSilm5vAgMB
AAEwDQYJKoZIhvcNAQELBQADggEBAFv+iWWz48Gig22U23v8xls9ymxzHysNl0vh
TrioQis1P17tnw22P0xHgf3QVJujFzWYnLdnDbRmv4l+5UZI7TKzjcrMjoPr9uzA
v1yEPDM0w5NVl19YLzgUZq4QVAMgykkneGrLxSIO0P89Tc7mW6x4kbU2buBr70z/
fezVIL7n/ysIsfsJ2Hq4RBSIGXQl+Tg+OlAyfT3zNwiZpmvmZ3ctGMaXbEYXgCWs
ILssKrao3Co7ef6uWqHXze3j32ZoP+OkxIxfilkvvJ/3wzlKpF2uwCYNybxefUCD
lYv3teoD7vZGnJAmvwlD28Cr6PEs4EQaHJ4tutZe6mWcQFAM5d4=
-----END CERTIFICATE-----
		"""	;
}
