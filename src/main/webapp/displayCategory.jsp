<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Set" %>
<%@ page import="ucl.ac.uk.main.Note" %>

<html>
<head>
    <title>Note Taking App</title>
    <jsp:include page="/meta.jsp"/>
</head>

<body>
<jsp:include page="/header.jsp"/>

<div class="container">
    <h2>Notes in Category: <%= request.getAttribute("category") %></h2>
    <ul class="list-group">
    <%
        Set<Note> notes = (Set<Note>) request.getAttribute("notes");
        if (notes != null && !notes.isEmpty()) {
            for (Note note : notes) {
                String noteId = note.getId();
                String noteName = note.getName();
    %>
                <li class="list-group-item">
                    <p><a href="displayNote?noteId=<%= noteId %>"><%= noteName %></a></p>
                </li>
    <%      } %>
    </ul>
    <%
        } else {
    %>
            <p>No notes found for this category.</p>
    <%
        }
    %>
</div>

<jsp:include page="/footer.jsp"/>
</body>
</html>
