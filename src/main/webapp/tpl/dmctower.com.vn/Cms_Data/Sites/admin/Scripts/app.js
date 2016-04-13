$(document).ready(function () {       
      if ($('html').hasClass('desktop')) {
        new WOW().init();
      }   
    });

$(function () {

  
    
    $("#headKeyword").keyup(function(event){
    if(event.keyCode == 13){
        $(".search-button").click();
    }
});
    
    
    
$(".search-button").click(function(){
if(location.href.indexOf("/en")!=-1)
{
  location.href="http://dmctower.com.vn/en/tim-kiem?key=" + $('#headKeyword').val();
}
  else
{
  
  location.href="../../../../search-key=.htm"/*tpa=http://dmctower.com.vn/search?key=*/ + $('#headKeyword').val();
}

  
  
        });
    
  
  
  
   $('#menu').dropdownMenu({ mode: ($.support.opacity ? 'slide' : 'showhide'), dropdownSelector: 'div.dropdown', fancy:{ mode: 'move', duration: 300, transition: 'easeInOutCubic' } });
  

    $('.ajaxForm').each(function () {
        var form = $(this);
        form.ajaxForm({
            sync: true,
            beforeSerialize: function ($form, options) {
            },
            beforeSend: function () {
                var form = $(this);
                form.find("[type=submit]").addClass("disabled").attr("disabled", true);
            },
            beforeSubmit: function (arr, $form, options) {
            },
            success: function (responseData, statusText, xhr, $form) {
                form.find("[type=submit]").removeClass("disabled").removeAttr("disabled");
                if (!responseData.Success) {
                    var validator = form.validate();
                    //                            var errors = [];
                    for (var i = 0; i < responseData.FieldErrors.length; i++) {
                        var obj = {};
                        var fieldName = responseData.FieldErrors[i].FieldName;
                        if (fieldName == "") {
                            alert(responseData.FieldErrors[i].ErrorMessage);
                        }
                        obj[fieldName] = responseData.FieldErrors[i].ErrorMessage;
                        validator.showErrors(obj);
                    }
                }
                else {
                    if (responseData.RedirectUrl != null) {
                        location.href = responseData.RedirectUrl;
                    }
                    else {
                        location.reload();
                    }

                }
            },
            error: function () {
                var form = $(this);
                form.find("[type=submit]").removeClass('disabled').removeAttr('disabled');
            }

        });
    })

})
