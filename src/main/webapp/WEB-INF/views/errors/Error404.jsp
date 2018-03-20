<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:error pageTitle="Error 404" imageFile="error404.jpg" error="${exception.message}">
</t:error>
