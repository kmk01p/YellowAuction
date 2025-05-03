/*체크박스 전체선택해제*/
function allCheckFunc(obj) {
	$("[name=ck]").prop("checked", $(obj).prop("checked") );
	
	/*체크박스 체크시 버튼 활성화 여부*/
    if($(".js-ck").is(":checked")){
        $(".js-active").attr("disabled", false);
    }else{
        $(".js-active").attr("disabled", true);
    }
}

/* 체크박스 체크시 전체선택 체크 여부 */
function oneCheckFunc(obj)
{
	var allObj = $("[name=all_ck]");
	var objName = $(obj).attr("name");

	if( $(obj).prop("checked") )
	{
		checkBoxLength = $("[name="+ objName +"]").length;
		checkedLength = $("[name="+ objName +"]:checked").length;

		if( checkBoxLength == checkedLength ) {
			allObj.prop("checked", true);
			$(".js-active").attr("disabled", false);
		} else {
			allObj.prop("checked", false);
		}
	}
	else
	{
		allObj.prop("checked", false);
		$(".js-active").attr("disabled", true);
	}
}

$(function(){
	$("[name=all_ck]").click(function(){
		allCheckFunc( this );
	});
	$("[name=ck]").each(function(){
		$(this).click(function(){
			oneCheckFunc( $(this) );
		});
	});
});


$(function(){
	/*탭*/
    $(".tab-content").hide();
    $(".tab-content:first-child").show();
    
    $(".click").click(function() {
        $(".click").removeClass("active");
        $(this).addClass("active");
        $(".tab-content").stop().hide()
        var activeTab = $(this).attr("rel");
        $("#" + activeTab).show();
    });
	
	
	/*포커스 인*/
	$(".js-frm-focus").on("keydown keyup", function() {
		/*데이터 없을 경우*/
		if($(this).val()=='')
			$(this).removeClass("active");
		else
			$(this).addClass("active");
	});
	
	
	/*해당 모달창 닫기*/
	$(".js-modal-master-close").click(function() {
        $(this).parents(".dim-layer").hide();
    });
	
	
	/*모달창*/
	$(".js-modal-open").click(function() {
        $(".js-modal").show();
    });
	$(".js-modal2-open").click(function() {
        $(".js-modal2").show();
    });
	$(".js-modal3-open").click(function() {
        $(".js-modal3").show();
    });
	$(".js-modal4-open").click(function() {
        $(".js-modal4").show();
    });
	$(".js-modal5-open").click(function() {
        $(".js-modal5").show();
    });
	$(".js-modal6-open").click(function() {
        $(".js-modal6").show();
    });
	$(".js-modal7-open").click(function() {
        $(".js-modal7").show();
    });
	$(".js-modal8-open").click(function() {
        $(".js-modal8").show();
    });
	$(".js-modal9-open").click(function() {
        $(".js-modal9").show();
    });
	$(".js-modal10-open").click(function() {
        $(".js-modal10").show();
    });
	
	
	/*삭제*/
	$(".js-delete").click(function() {
        $(this).parents(".item").remove();
    });
	
	
	/*재시도*/
	$(".js-reload").click(function() {
        $(this).parents(".qr-group").removeClass("inactive");
    });
	
	
	/*코멘트창*/
	$(".js-comment").on("keydown keyup", function() {
		/*입력창 높이 변화*/
		$(this).height(1).height($(this).prop("scrollHeight")-24);
	});
	
	
	/*디엠창*/
	$(".js-chat").on("keydown keyup", function() {
		/*입력창 높이 변화*/
		$(this).height(1).height($(this).prop("scrollHeight"));
	});
	
	
	/*복권 뽑기*/
	$(".js-blind").click(function() {
        $(this).removeClass("blind");
    });

});

function resetAcodian(){
	var acodian = {

		click: function(target) {
			var _self = this,
				$target = $(target);
			$target.on("click", function() {
				var $this = $(this);
				if ($this.next(".js-unfold").css("display") == "none") {
					$(".js-unfold").slideUp();
					_self.onremove($target);

					$this.addClass("active");
					$this.parent().addClass("active");
					$this.next().slideDown();
				} else {
					$(".js-unfold").slideUp();
					_self.onremove($target);

				}
			});
		},
		onremove: function($target) {
			$target.removeClass("active");
			$target.parent().removeClass("active");
		}

	};
	acodian.click(".js-fold");
}