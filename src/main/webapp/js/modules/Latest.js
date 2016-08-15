var latest = '';

$(function() {
	jQuery.fn.exists = function() {
		return this.length > 0;
	}

	latest = {
		config : {
			isPostBack : false,
			async : false,
			cache : false,
			type : 'POST',
			contentType : "application/json; charset=utf-8",
			data : '{}',
			dataType : 'json',
			rootURL : "REST/",
			baseURL : "REST/bikes/",
			method : "",
			url : "",
			ajaxCallMode : 0
		},

		ajaxCall : function(config) {
			$.ajax({
				type : latest.config.type,
				beforeSend : function(request) {
					// Need to send oAuth values here
				},
				contentType : latest.config.contentType,
				cache : latest.config.cache,
				async : latest.config.async,
				url : latest.config.url,
				data : latest.config.data,
				dataType : latest.config.dataType,
				success : latest.ajaxSuccess,
				error : latest.ajaxFailure
			});
		},

		FindLatestBikesFromDB : function() {
			this.config.url = this.config.baseURL + "GetLatestBikesFromDB";
			this.config.ajaxCallMode = 1;
			this.ajaxCall(this.config);
			return false;
		},

		FillScrollContainer : function(bikes) {
			$
					.each(
							bikes,
							function(index, value) {
								var bikeDetail = '<div data-p="112.50"><img data-u="image" src=".'
										+ value.primaryimage
										+ '"/><div data-u="caption" data-t="0" style="position: absolute; top: 320px; left: 30px; width: 350px; height: 30px; background-color: rgba(235,81,0,0.5); font-size: 20px; color: #ffffff; line-height: 30px; text-align: center;">'
										+ value.title
										+ '<div><p><a href="./ItemDetails.jsp?bike='
										+ value.id
										+ '>'
										+ value.description
										+ '</a></p></div></div></div>';

								$('#sliderContainer').append(bikeDetail);
							});

			if (bikes.length > 0) {
				$("#bulletNav").show();
				$("#arrowNav").show();
				latest.InitializeSlider();
			} else {
				csscody.info("<h2>" + 'Information Message' + "</h2><p>"
						+ 'No Bikes in the Store!' + "</p>");
			}
			return false;
		},

		InitializeSlider : function(msg) {
			var jssor_1_SlideoTransitions = [ [ {
				b : 0,
				d : 600,
				y : -290,
				e : {
					y : 27
				}
			} ], [ {
				b : 0,
				d : 1000,
				y : 185
			}, {
				b : 1000,
				d : 500,
				o : -1
			}, {
				b : 1500,
				d : 500,
				o : 1
			}, {
				b : 2000,
				d : 1500,
				r : 360
			}, {
				b : 3500,
				d : 1000,
				rX : 30
			}, {
				b : 4500,
				d : 500,
				rX : -30
			}, {
				b : 5000,
				d : 1000,
				rY : 30
			}, {
				b : 6000,
				d : 500,
				rY : -30
			}, {
				b : 6500,
				d : 500,
				sX : 1
			}, {
				b : 7000,
				d : 500,
				sX : -1
			}, {
				b : 7500,
				d : 500,
				sY : 1
			}, {
				b : 8000,
				d : 500,
				sY : -1
			}, {
				b : 8500,
				d : 500,
				kX : 30
			}, {
				b : 9000,
				d : 500,
				kX : -30
			}, {
				b : 9500,
				d : 500,
				kY : 30
			}, {
				b : 10000,
				d : 500,
				kY : -30
			}, {
				b : 10500,
				d : 500,
				c : {
					x : 87.50,
					t : -87.50
				}
			}, {
				b : 11000,
				d : 500,
				c : {
					x : -87.50,
					t : 87.50
				}
			} ], [ {
				b : 0,
				d : 600,
				x : 410,
				e : {
					x : 27
				}
			} ], [ {
				b : -1,
				d : 1,
				o : -1
			}, {
				b : 0,
				d : 600,
				o : 1,
				e : {
					o : 5
				}
			} ], [ {
				b : -1,
				d : 1,
				c : {
					x : 175.0,
					t : -175.0
				}
			}, {
				b : 0,
				d : 800,
				c : {
					x : -175.0,
					t : 175.0
				},
				e : {
					c : {
						x : 7,
						t : 7
					}
				}
			} ], [ {
				b : -1,
				d : 1,
				o : -1
			}, {
				b : 0,
				d : 600,
				x : -570,
				o : 1,
				e : {
					x : 6
				}
			} ], [ {
				b : -1,
				d : 1,
				o : -1,
				r : -180
			}, {
				b : 0,
				d : 800,
				o : 1,
				r : 180,
				e : {
					r : 7
				}
			} ], [ {
				b : 0,
				d : 1000,
				y : 80,
				e : {
					y : 24
				}
			}, {
				b : 1000,
				d : 1100,
				x : 570,
				y : 170,
				o : -1,
				r : 30,
				sX : 9,
				sY : 9,
				e : {
					x : 2,
					y : 6,
					r : 1,
					sX : 5,
					sY : 5
				}
			} ], [ {
				b : 2000,
				d : 600,
				rY : 30
			} ], [ {
				b : 0,
				d : 500,
				x : -105
			}, {
				b : 500,
				d : 500,
				x : 230
			}, {
				b : 1000,
				d : 500,
				y : -120
			}, {
				b : 1500,
				d : 500,
				x : -70,
				y : 120
			}, {
				b : 2600,
				d : 500,
				y : -80
			}, {
				b : 3100,
				d : 900,
				y : 160,
				e : {
					y : 24
				}
			} ], [ {
				b : 0,
				d : 1000,
				o : -0.4,
				rX : 2,
				rY : 1
			}, {
				b : 1000,
				d : 1000,
				rY : 1
			}, {
				b : 2000,
				d : 1000,
				rX : -1
			}, {
				b : 3000,
				d : 1000,
				rY : -1
			}, {
				b : 4000,
				d : 1000,
				o : 0.4,
				rX : -1,
				rY : -1
			} ] ];

			var jssor_1_options = {
				$AutoPlay : true,
				$Idle : 2000,
				$CaptionSliderOptions : {
					$Class : $JssorCaptionSlideo$,
					$Transitions : jssor_1_SlideoTransitions,
					$Breaks : [ [ {
						d : 2000,
						b : 1000
					} ] ]
				},
				$ArrowNavigatorOptions : {
					$Class : $JssorArrowNavigator$
				},
				$BulletNavigatorOptions : {
					$Class : $JssorBulletNavigator$
				}
			};

			var jssor_1_slider = new $JssorSlider$("jssor_1", jssor_1_options);

			// responsive code begin
			// you can remove responsive code if you don't want the slider
			// scales while window resizing
			function ScaleSlider() {
				var refSize = jssor_1_slider.$Elmt.parentNode.clientWidth;
				if (refSize) {
					refSize = Math.min(refSize, 600);
					jssor_1_slider.$ScaleWidth(refSize);
				} else {
					window.setTimeout(ScaleSlider, 30);
				}
			}
			ScaleSlider();
			$(window).bind("load", ScaleSlider);
			$(window).bind("resize", ScaleSlider);
			$(window).bind("orientationchange", ScaleSlider);
			// responsive code end
		},

		ajaxSuccess : function(msg) {
			switch (latest.config.ajaxCallMode) {
			case 0:
				break;

			case 1:
				latest.FillScrollContainer(msg);
				break;

			}
		},

		ajaxFailure : function(msg) {
			switch (latest.config.ajaxCallMode) {
			case 0:
				break;
			case 1:
				csscody.error("<h2>" + 'Error Message' + "</h2><p>"
						+ 'Cannot load Bikes from the Store!' + "</p>");
				break;

			}
		},

		init : function(config) {
			latest.FindLatestBikesFromDB();
		}
	};

	latest.init();

});