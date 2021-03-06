/*
* Template Name: Unify - Responsive Bootstrap Template
* Author: @htmlstream
* Website: http://htmlstream.com
*/

var App = function () {
	// We extend jQuery by method hasAttr
	
	function loadScripts() {
		$.getScript( "/content/assets/plugins/datatables/dataTables.responsive.min.js", function( data, textStatus, jqxhr ) { });
		$.getScript( "/content/assets/plugins/datatables/responsive.bootstrap.min.js", function( data, textStatus, jqxhr ) { });
	}
	
	$.fn.hasAttr = function(name) {
	  return this.attr(name) !== undefined;
	};

	// Fixed Header
	function handleHeader() {
		jQuery(window).scroll(function() {
		  if (jQuery(window).scrollTop() > 100) {
			jQuery('.header-fixed .header-sticky').addClass('header-fixed-shrink');
		  } else {
			jQuery('.header-fixed .header-sticky').removeClass('header-fixed-shrink');
		  }
		});
	}

	// Header Mega Menu
	function handleMegaMenu() {
		jQuery(document).on('click', '.mega-menu .dropdown-menu', function(e) {
			e.stopPropagation();
		})
	}

	// Search Box (Header)
	function handleSearch() {
		jQuery('.search').on("click", function () {
			if(jQuery('.search-btn').hasClass('fa-search')){
				jQuery('.search-open').fadeIn(500);
				jQuery('.search-btn').removeClass('fa-search');
				jQuery('.search-btn').addClass('fa-times');
			} else {
				jQuery('.search-open').fadeOut(500);
				jQuery('.search-btn').addClass('fa-search');
				jQuery('.search-btn').removeClass('fa-times');
			}
		});
	}

	// Search Box v1 (Header v5)
	function handleSearchV1() {
		jQuery('.header-v5 .search-button').click(function () {
			jQuery('.header-v5 .search-open').slideDown();
		});

		jQuery('.header-v5 .search-close').click(function () {
			jQuery('.header-v5 .search-open').slideUp();
		});

		jQuery(window).scroll(function(){
		  if(jQuery(this).scrollTop() > 1) jQuery('.header-v5 .search-open').fadeOut('fast');
		});
	}

	// Search Box v2 (Header v8)
	function handleSearchV2() {
		$(".blog-topbar .search-btn").on("click", function() {
		  if (jQuery(".topbar-search-block").hasClass("topbar-search-visible")) {
			jQuery(".topbar-search-block").slideUp();
			jQuery(".topbar-search-block").removeClass("topbar-search-visible");
		  } else {
			jQuery(".topbar-search-block").slideDown();
			jQuery(".topbar-search-block").addClass("topbar-search-visible");
		  }
		});
		$(".blog-topbar .search-close").on("click", function() {
		  jQuery(".topbar-search-block").slideUp();
		  jQuery(".topbar-search-block").removeClass("topbar-search-visible");
		});
		jQuery(window).scroll(function() {
		  jQuery(".topbar-search-block").slideUp();
		  jQuery(".topbar-search-block").removeClass("topbar-search-visible");
		});
	}

	// TopBar (Header v8)
	function handleTopBar() {
		$(".topbar-toggler").on("click", function() {
		  if (jQuery(".topbar-toggler").hasClass("topbar-list-visible")) {
			jQuery(".topbar-menu").slideUp();
			jQuery(this).removeClass("topbar-list-visible");
		  } else {
			jQuery(".topbar-menu").slideDown();
			jQuery(this).addClass("topbar-list-visible");
		  }
		});
	}

	// TopBar SubMenu (Header v8)
	function handleTopBarSubMenu() {
		$(".topbar-list > li").on("click", function(e) {
		  if (jQuery(this).children("ul").hasClass("topbar-dropdown")) {
			if (jQuery(this).children("ul").hasClass("topbar-dropdown-visible")) {
			  jQuery(this).children(".topbar-dropdown").slideUp();
			  jQuery(this).children(".topbar-dropdown").removeClass("topbar-dropdown-visible");
			} else {
			  jQuery(this).children(".topbar-dropdown").slideDown();
			  jQuery(this).children(".topbar-dropdown").addClass("topbar-dropdown-visible");
			}
		  }
		  //e.preventDefault();
		});
	}

	// Sidebar Navigation Toggle
	function handleToggle() {
		jQuery('.list-toggle').on('click', function() {
			jQuery(this).toggleClass('active');
		});
	}

	// Equal Height Columns
	function handleEqualHeightColumns() {
		var EqualHeightColumns = function () {
			$(".equal-height-columns").each(function() {
				heights = [];
				$(".equal-height-column", this).each(function() {
					$(this).removeAttr("style");
					heights.push($(this).height()); // write column's heights to the array
				});
				$(".equal-height-column", this).height(Math.max.apply(Math, heights)); //find and set max
			});
		}

		EqualHeightColumns();
		$(window).resize(function() {
			EqualHeightColumns();
		});
		$(window).load(function() {
			EqualHeightColumns();
		});
	}

	// Equal Height Image-Columns
	function handleEqualHeightColumns__Images() {
		var EqualHeightColumns__Images = function () {
			$('.equal-height-columns-v2').each(function() {
				var heights = [];
				$('.equal-height-column-v2', this).each(function() {
					$(this).removeAttr('style');
					heights.push($(this).height()); // Write column's heights to the array
				});
				$('.equal-height-column-v2', this).height(Math.max.apply(Math, heights)); // Find and set max

				$('.equal-height-column-v2', this).each(function() {
					if ($(this).hasAttr('data-image-src')) {
						$(this).css('background', 'url('+$(this).attr('data-image-src')+') no-repeat scroll 50% 0 / cover');
					}
				});
			});
		}
    $('.equal-height-columns-v2').ready(function() {
      EqualHeightColumns__Images();
    });
		$(window).resize(function() {
			EqualHeightColumns__Images();
		});
	}

	// Full Screen
	var handleFullscreen = function() {
		var WindowHeight = $(window).height();
		var HeaderHeight = 0;

		if ($(document.body).hasClass("promo-padding-top")) {
		  HeaderHeight = $(".header").height();
		} else {
		  HeaderHeight = 0;
		}

		$(".fullheight").css("height", WindowHeight - HeaderHeight);

		$(window).resize(function() {
		  var WindowHeight = $(window).height();
		  $(".fullheight").css("height", WindowHeight - HeaderHeight);
		});
	}

	// Align Middle
	var handleValignMiddle = function() {
		$(".valign__middle").each(function() {
		  $(this).css("padding-top", $(this).parent().height() / 2 - $(this).height() / 2);
		});
		$(window).resize(function() {
		  $(".valign__middle").each(function() {
			$(this).css("padding-top", $(this).parent().height() / 2 - $(this).height() / 2);
		  });
		});
	}

	// Hover Selector
	function handleHoverSelector() {
		// $('.hoverSelector').on('hover', function(e) {
		// 	$('.hoverSelectorBlock', this).toggleClass('show');
		// 	e.stopPropagation();
		// });
	    $('.hoverSelector').on('click', function(e) {
	      if (jQuery(this).children('ul').hasClass('languages')) {
	        if (jQuery(this).children('ul').hasClass('languages-visible')) {
	          jQuery(this).children('.languages').slideUp();
	          jQuery(this).children('.languages').removeClass('languages-visible');
	        } else {
	          jQuery(this).children('.languages').slideDown();
	          jQuery(this).children('.languages').addClass('languages-visible');
	        }
	      }
	      //e.preventDefault();
	    });
	}

	// Bootstrap Tooltips and Popovers
	function handleBootstrap() {
		/* Bootstrap Carousel */
		jQuery('.carousel').carousel({
			interval: 15000,
			pause: 'hover'
		});

		/* Tooltips */
		jQuery('.tooltips').tooltip();
		jQuery('.tooltips-show').tooltip('show');
		jQuery('.tooltips-hide').tooltip('hide');
		jQuery('.tooltips-toggle').tooltip('toggle');
		jQuery('.tooltips-destroy').tooltip('destroy');

		/* Popovers */
		jQuery('.popovers').popover();
		jQuery('.popovers-show').popover('show');
		jQuery('.popovers-hide').popover('hide');
		jQuery('.popovers-toggle').popover('toggle');
		jQuery('.popovers-destroy').popover('destroy');
	}
	
	function handleTables() {
		try {
		console.log('kamini');
		var otable =  $('#datatable_fixed_column').DataTable({
			 initComplete: function () {
		            this.api().columns().every( function () {
		                var column = this;
		                var select = $('<select><option value=""></option></select>')
		                    .appendTo( $(column.footer()).empty() )
		                    .on( 'change', function () {
		                        var val = $.fn.dataTable.util.escapeRegex(
		                            $(this).val()
		                        );
		 
		                        column
		                            .search( val ? '^'+val+'$' : '', true, false )
		                            .draw();
		                    } );
		 
		                column.data().unique().sort().each( function ( d, j ) {
		                    select.append( '<option value="'+d+'">'+d+'</option>' )
		                } );
		            } );
		        }, 
			 responsive: true, 
			 stateSave: true, 
			 "pageLength": 50
			 });
	} catch (err) {
		console.log(err);
	}
	
	
	
	$('.datatable_report').each(function(i, obj) {
		var tableID  = $(this).attr('id');
		var typeOfReport = $("#"+tableID).data('graph_type');
		console.log('typeOfReport->'+ typeOfReport);
		if(typeOfReport == 'table') {
		$('#'+tableID).DataTable({
			"pageLength": 16, 
			initComplete: function () {
		            this.api().columns().every( function () {
		                var column = this;
		                var select = $('<select><option value=""></option></select>')
		                    .appendTo( $(column.footer()).empty() )
		                    .on( 'change', function () {
		                        var val = $.fn.dataTable.util.escapeRegex(
		                            $(this).val()
		                        );
		 
		                        column
		                            .search( val ? '^'+val+'$' : '', true, false )
		                            .draw();
		                    } );
		 
		                column.data().unique().sort().each( function ( d, j ) {
		                    select.append( '<option value="'+d+'">'+d+'</option>' )
		                } );
		            } );
		        }, 
			 responsive: true, 
			 stateSave: true, 
			 "pageLength": 50
			 });
	}
	});
	
	}
	
	function handleGraphs() {
		try {
			console.log("graoh foujd -->");
			$('.datatable_report').each(function(i, obj) {
				var tableID  = $(this).attr('id');
			    var containerID = '#'+$(this).data('graph_containter');
			    var graph_type = $(this).data('graph_type');
			    if(graph_type.indexOf('table')<=-1)
			    	{
			    	 console.log("tableID -->" + $(this).attr('id'));
						console.log("containerID -->" + containerID);
						 var graph_title = $(this).data('graph_title');
						
						 $(containerID).highcharts({
						        data: {
						            table: tableID
						        },
						        chart: {
						            type: graph_type, 
						            options3d: {
						                enabled: true,
						                alpha: 45
						            }
						        },title: {
						            text: graph_title
						        },
						        tooltip: {
						            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
						        },
						        plotOptions: {
						            pie: {
						                allowPointSelect: true,
						                cursor: 'pointer',
						                dataLabels: {
						                    enabled: true,
						                    format: '<b>{point.name}</b>: {point.percentage:.1f} %',
						                    style: {
						                        color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
						                    }
						                }
						            }
						        }
						    });
			    	}
			    
			   
			});
			
			//Hide Table
			//$('.dataTables_wrapper').hide();
		
	} catch (err) {
		console.log(err);
	}
	}
	
	function handleCalendar() {
		try {
			console.log("Init Calendar");
			$('#calendar').fullCalendar({
				header: {
					left: 'prev,next today',
					center: 'title',
					right: 'month,basicWeek,basicDay'
				},
				defaultDate: '2016-01-12',
				editable: true,
				eventLimit: true, // allow "more" link when too many events
				events: [
					{
						title: 'All Day Event',
						start: '2016-01-01'
					},
					{
						title: 'Long Event',
						start: '2016-01-07',
						end: '2016-01-10'
					},
					{
						id: 999,
						title: 'Repeating Event',
						start: '2016-01-09T16:00:00'
					},
					{
						id: 999,
						title: 'Repeating Event',
						start: '2016-01-16T16:00:00'
					},
					{
						title: 'Conference',
						start: '2016-01-11',
						end: '2016-01-13'
					},
					{
						title: 'Meeting',
						start: '2016-01-12T10:30:00',
						end: '2016-01-12T12:30:00'
					},
					{
						title: 'Lunch',
						start: '2016-01-12T12:00:00'
					},
					{
						title: 'Meeting',
						start: '2016-01-12T14:30:00'
					},
					{
						title: 'Happy Hour',
						start: '2016-01-12T17:30:00'
					},
					{
						title: 'Dinner',
						start: '2016-01-12T20:00:00'
					},
					{
						title: 'Birthday Party',
						start: '2016-01-13T07:00:00'
					},
					{
						title: 'Click for Google',
						url: 'http://google.com/',
						start: '2016-01-28'
					}
				]
			});
		
	} catch (err) {
		console.log(err);
	}
	}

	return {
		init: function () {
			handleBootstrap();
			handleSearch();
			handleSearchV1();
			handleSearchV2();
			handleTopBar();
			handleTopBarSubMenu();
			handleToggle();
			handleHeader();
			handleMegaMenu();
			handleHoverSelector();
			handleFullscreen();
			handleValignMiddle();
			handleEqualHeightColumns();
			handleEqualHeightColumns__Images();
			handleTables();
			handleGraphs();
			handleCalendar();
			loadScripts();
		},

		// Counters
		initCounter: function () {
			jQuery('.counter').counterUp({
				delay: 10,
				time: 1000
			});
		},

		// Parallax Backgrounds
		initParallaxBg: function () {
			jQuery(window).load(function() {
				jQuery('.parallaxBg').parallax("50%", 0.2);
				jQuery('.parallaxBg1').parallax("50%", 0.4);
			});
		},

		// Scroll Bar
		initScrollBar: function () {
			jQuery('.mCustomScrollbar').mCustomScrollbar({
				theme:"minimal",
				scrollInertia: 200,
				scrollEasing: "linear"
			});
		},

		// Sidebar Menu Dropdown
		initSidebarMenuDropdown: function() {
		  function SidebarMenuDropdown() {
			jQuery('.header-v7 .dropdown-toggle').on('click', function() {
			  jQuery('.header-v7 .dropdown-menu').stop(true, false).slideUp();
			  jQuery('.header-v7 .dropdown').removeClass('open');

			  if (jQuery(this).siblings('.dropdown-menu').is(":hidden") == true) {
				jQuery(this).siblings('.dropdown-menu').stop(true, false).slideDown();
				jQuery(this).parents('.dropdown').addClass('open');
			  }
			});
		  }
		  SidebarMenuDropdown();
		},

		// Animate Dropdown
		initAnimateDropdown: function() {
		  function MenuMode() {
			jQuery('.dropdown').on('show.bs.dropdown', function() {
			  jQuery(this).find('.dropdown-menu').first().stop(true, true).slideDown();
			});
			jQuery('.dropdown').on('hide.bs.dropdown', function() {
			  jQuery(this).find('.dropdown-menu').first().stop(true, true).slideUp();
			});
		  }

		  jQuery(window).resize(function() {
			if (jQuery(window).width() > 768) {
			  MenuMode();
			}
		  });

		  if (jQuery(window).width() > 768) {
			MenuMode();
		  }
		},
		
		initCalendar: function() {
			
		}
	};
}();
