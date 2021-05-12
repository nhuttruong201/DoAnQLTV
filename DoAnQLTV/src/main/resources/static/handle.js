var allNum = 0;
var arrIdBook = [];
var arrNumBook = [];

function updateIdBookSubmit(arr) {
    $(".bookId").val("");
    $("#numBookType").val(arr.length);
    for (var i = 0; i < arr.length; i++) {
        $("#bookId_" + (i + 1)).val(arr[i]);
    }
}

function updateNumBookSubmit(arr) {
    $(".numBook").val("");
    for (var i = 0; i < arr.length; i++) {
        $("#numBook_" + (i + 1)).val(arr[i]);
    }
}

function MoveBook(id) {
    //todo: cập nhật lại mảng ID book
    for (var i = 0; i < arrIdBook.length; i++) {
        if (parseInt(arrIdBook[i]) == parseInt(id)) {
            allNum -= arrNumBook[i];
            arrIdBook.splice(i, 1);
            arrNumBook.splice(i, 1);
            // alert("Loại sách: " + ($("#numBookType").val() - 1) + ", Tổng sách: " + allNum + "\nMảng ID hiện tại: " + arrIdBook + "\nMảng SL hiện tại: " + arrNumBook);
            break;
        }
    }
    updateIdBookSubmit(arrIdBook);
    updateNumBookSubmit(arrNumBook);
    $("#tr_" + id).remove();
}

$(document).ready(function() {
    $("#btnYeuCauMuonSach").addClass("active");
    $("#selectBookId").change(function() {
        var url = "/api/search-book/" + $("#selectBookId").val();
        $.get(url, function(data) {
            // alert("ID:" + $('#selectBookId').val() + ": " + data.tensach);
            $("#bookName").val(data.tensach);
        });
    });

    $("#btnAddBook").click(function() {
        allNum += parseInt($("#sl").val());
        if (allNum > 5) {
            allNum -= parseInt($("#sl").val());
            alert("\n\nChỉ được mượn tối đa 5 quyển sách!\n\n");
        } else {
            arrIdBook.push($("#selectBookId").val());
            updateIdBookSubmit(arrIdBook);
            arrNumBook.push($("#sl").val());
            updateNumBookSubmit(arrNumBook);
            var bookId = $("#selectBookId").val();
            var bookName = $("#bookName").val();
            var sl = $("#sl").val();
            // var btnEdit = '<button type="button" class="btn btn-outline-primary btn-sm"><i class="fas fa-pencil-alt"></i></button> ';
            var btnDelete =
                '<button type="button" onclick="MoveBook(' +
                bookId +
                ')" class="btn btn-outline-primary btn-sm"><i class="far fa-trash-alt"></i></button>';
            var idTr = "tr_" + bookId;
            $("#listBookBorrow").append(
                "<tr id='" +
                idTr +
                "'><td>" +
                bookId +
                "</td><td>" +
                bookName +
                "</td><td>" +
                sl +
                "</td><td class='text-center'>" +
                btnDelete +
                "</td></tr>"
            );
            //todo: reset select book Id
            $("#selectBookId").prop("selectedIndex", 0);
            $("#bookName").val("...");
            $("#sl").val(1);
        }
    });
});