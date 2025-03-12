<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="ucl.ac.uk.main.Note" %>
<%@ page import="java.util.Set" %>

<html>
<head>
  <meta charset="UTF-8">
  <title>Edit Note</title>
  <jsp:include page="/meta.jsp"/>
</head>


<body>
<jsp:include page="/header.jsp"/>

<div class="container">
    <h2>Edit Note</h2>
    <%
        Note noteToEdit = (Note) request.getAttribute("noteToEdit");
        Set<String> categories = (Set<String>) request.getAttribute("categories");
        if (noteToEdit == null) {
    %>
        <p>Note not found</p>
    <%
        } else {
    %>
    <form method="POST" action="updateNote">

        <div class="form-group">
          <label for="newName">Note Name:</label>
          <input type="hidden" name="noteId" value="<%= noteToEdit.getId() %>">
          <input class="form-control" type="text" id="name" name="name" value="<%= noteToEdit.getName() %>">
        </div>

        <div class="form-group">
            <label for="content">Make Changes:</label>
            <textarea class="form-control" rows="10" id="content" name="content"><%= noteToEdit.getContent() %></textarea>
        </div>

        <div class="form-check">
            <label for="categories">Categories:</label>
            <%
                Set<String> noteCategories = noteToEdit.getCategories();

                for (String category : categories) {
                    if (noteCategories.contains(category)) {
            %>
                        <input class="form-check-input" type="checkbox" id="<%= category %>" name="categories" value="<%= category %>" checked>
                        <label class="form-check-label"><%= category %></label>
            <%      } else { %>
                        <input class="form-check-input" type="checkbox" id="<%= category %>" name="categories" value="<%= category %>">
                        <label class="form-check-label"><%= category %></label>
            <%      }
                }
            %>
        </div>

        <input class="form-control btn-success" type="submit" value="Save Changes">
    </form>
    <%
        }
    %>
</div>

<jsp:include page="/footer.jsp"/>
</body>
</html>