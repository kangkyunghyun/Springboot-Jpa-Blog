let index = {
    init: function () {
        $("#btn-save").on("click", ()=>{
            this.save();
        });
        $("#btn-delete").on("click", ()=>{
            this.deleteById();
        });
        $("#btn-update").on("click", ()=>{
            this.update();
        });
        $("#btn-reply-save").on("click", ()=>{
            this.replySave();
        });
    },

    save: function () {
        // alert("user의 save함수 호출됨");
        let data = {
            title: $("#title").val(),
            content: $("#content").val(),
        };

        $.ajax({
            type: "POST",
            url: "/api/board",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done(function (res) {
            alert("글 작성이 완료되었습니다.");
            location.href = "/";
        }).fail(function (err) {
            alert(JSON.stringify(err));
        });
    },

    deleteById: function () {
        let id = $("#id").text();
        $.ajax({
            type: "DELETE",
            url: "/api/board/"+id,
            dataType: "json"
        }).done(function (res) {
            alert("글 삭제가 완료되었습니다.");
            location.href = "/";
        }).fail(function (err) {
            alert(JSON.stringify(err));
        });
    },

    update: function () {
        let id = $("#id").val();

        let data = {
            title: $("#title").val(),
            content: $("#content").val(),
        };

        $.ajax({
            type: "PUT",
            url: "/api/board/"+id,
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done(function (res) {
            alert("글 수정이 완료되었습니다.");
            location.href = "/";
        }).fail(function (err) {
            alert(JSON.stringify(err));
        });
    },
    replySave: function () {
        // alert("user의 save함수 호출됨");
        let data = {
            userId: $("#userId").val(),
            boardId: $("#boardId").val(),
            content: $("#reply-content").val(),
        };

        $.ajax({
            type: "POST",
            url: `/api/board/${data.boardId}/reply`,
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done(function (res) {
            alert("댓글 작성이 완료되었습니다.");
            location.href = `/board/${data.boardId}`;
        }).fail(function (err) {
            alert(JSON.stringify(err));
        });
    },
    replyDelete: function (boardId, replyId) {

        $.ajax({
            type: "DELETE",
            url: `/api/board/${boardId}/reply/${replyId}`,
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done(function (res) {
            alert("댓글 삭제 완료되었습니다.");
            location.href = `/board/${boardId}`;
        }).fail(function (err) {
            alert(JSON.stringify(err));
        });
    },
}

index.init();