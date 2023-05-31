$(document).ready(function() {
	
	(function($) {

		"use strict";

		var fullHeight = function() {

			$('.js-fullheight').css('height', $(window).height());
			$(window).resize(function(){
				$('.js-fullheight').css('height', $(window).height());
			});

		};
		fullHeight();

	})(jQuery);
	
	$("#next-btn").click(function(){
		var currentDiv = $('#testPoster');
		var nextDiv = $('#testQuestions');

		currentDiv.addClass('visually-hidden');
		nextDiv.removeClass('visually-hidden');
	});

	var path = window.location.pathname;
	var page = sPath.substring(path.lastIndexOf('/') + 1);
});
