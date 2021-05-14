var allNum = 0;
var arrIdBookBorrow = [];
var arrNumBookBorrow = [];

$(document).ready(function() {
    $("#btnYeuCauMuonSach").addClass("active");
    $("#selectBookId").change(function() {
        var url = "/api/search-book/" + $("#selectBookId").val();
        $.get(url, function(data) {
            $("#bookName").val(data.tensach);
            $("#numBookCurrent").val(data.soluong);
        });
    });

    $("#btnAddBook").click(function() {
        var slSachMuon = $("#sl").val();
        var slSachConLai = $("#numBookCurrent").val();
        // check sách tồn tại trong danh sách mượn
        if (checkExistInListBookBorrow(arrIdBookBorrow, $("#selectBookId").val())) {
            alert("\n\nSách đã tồn tại trong danh sách mượn!\n\n")
        }
        // check sl sách mượn nhiều hơn sách hiện còn
        else if (slSachMuon > slSachConLai) {
            alert("\n\nKhông đủ sách!\n\n");
        } else {
            allNum += parseInt($("#sl").val());
            if (allNum > 5) {
                allNum -= parseInt($("#sl").val());
                alert("\n\nChỉ được mượn tối đa 5 quyển sách!\n\n");
            } else {
                arrIdBookBorrow.push($("#selectBookId").val());
                updateIdBookSubmit(arrIdBookBorrow);
                arrNumBookBorrow.push($("#sl").val());
                updateNumBookSubmit(arrNumBookBorrow);
                var bookId = $("#selectBookId").val();
                var bookName = $("#bookName").val();
                var sl = $("#sl").val();
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
        }
    });
});

function checkExistInListBookBorrow(arrIdBook, id) {
    for (var i = 0; i < arrIdBook.length; i++) {
        if (parseInt(arrIdBook[i]) == parseInt(id)) {
            return true;
        }
    }
}

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
    for (var i = 0; i < arrIdBookBorrow.length; i++) {
        if (parseInt(arrIdBookBorrow[i]) == parseInt(id)) {
            allNum -= arrNumBookBorrow[i];
            arrIdBookBorrow.splice(i, 1);
            arrNumBookBorrow.splice(i, 1);
            // alert("Loại sách: " + ($("#numBookType").val() - 1) + ", Tổng sách: " + allNum + "\nMảng ID hiện tại: " + arrIdBook + "\nMảng SL hiện tại: " + arrNumBook);
            break;
        }
    }
    updateIdBookSubmit(arrIdBookBorrow);
    updateNumBookSubmit(arrNumBookBorrow);
    $("#tr_" + id).remove();
}