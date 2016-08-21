<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@ page import="java.util.*"%>

<% String url = request.getRequestURL().toString();
String baseURL = url.substring(0, url.length() - request.getRequestURI().length()) + request.getContextPath() + "/";
%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<head>
<title>Content Admin | iStar CMS</title>

<!-- Meta -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<!-- Favicon -->
<link rel="shortcut icon" href="favicon.ico">

<!-- Web Fonts -->
<link rel='stylesheet' type='text/css' href='//fonts.googleapis.com/css?family=Open+Sans:400,300,600&amp;subset=cyrillic,latin'>

<!-- CSS Global Compulsory -->
<link rel="stylesheet" href="<%=baseURL %>assets/plugins/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=baseURL %>assets/css/style.css">

<!-- CSS Header and Footer -->
<link rel="stylesheet" href="<%=baseURL %>assets/css/headers/header-default.css">
<link rel="stylesheet" href="<%=baseURL %>assets/css/footers/footer-v1.css">

<!-- CSS Implementing Plugins -->
<link rel="stylesheet" href="<%=baseURL %>assets/plugins/animate.css">
<link rel="stylesheet" href="<%=baseURL %>assets/plugins/line-icons/line-icons.css">
<link rel="stylesheet" href="<%=baseURL %>assets/plugins/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet" href="<%=baseURL %>assets/css/business.style.css">
<link rel="stylesheet" href="<%=baseURL %>assets/css/global.css">
<link rel="stylesheet" href="<%=baseURL%>assets/css/pages/profile.css">
<link rel="stylesheet" href="<%=baseURL%>assets/css/app.css">

<!-- CSS Theme -->
<link rel="stylesheet" href="<%=baseURL %>assets/css/theme-colors/default.css" id="style_color">
<link rel="stylesheet" href="<%=baseURL %>assets/css/theme-colors/orange.css" id="style_color">

<!-- CSS Customization -->
<link rel="stylesheet" href="<%=baseURL %>assets/css/custom.css">
</head>

<body>

	<div class="wrapper">
		<jsp:include page="/includes/header.jsp"></jsp:include>
		<div class="breadcrumbs">
			<div class="container-fluid ">
				<h1 class="pull-left">Create Qualification Pack</h1>
				<ul class="pull-right breadcrumb">
					<li><a href="index.html">Home</a></li>
					<li><a href="">Content Admin </a></li>
					<li class="active">Create Qualification Pack</li>
				</ul>
			</div>
		
		</div>
		<div class="container-fluid height-1000" style="padding: 0px !important">
			<!-- Begin Content -->
				<div class="col-md-9">
					<!-- General Unify Forms -->
					<form action="#" class="sky-form">
						<header>General Unify Forms</header>

						<fieldset>
							<section>
								<label class="label">Text input</label>
								<label class="input">
									<input type="text">
								</label>
							</section>

							<section>
								<label class="label">File input</label>
								<label for="file" class="input input-file">
									<div class="button"><input type="file" id="file" onchange="this.parentNode.nextSibling.value = this.value">Browse</div><input type="text" readonly>
								</label>
							</section>

							<section>
								<label class="label">Input with autocomlete</label>
								<label class="input">
									<input type="text" list="list">
									<datalist id="list">
										<option value="Alexandra"></option>
										<option value="Alice"></option>
										<option value="Anastasia"></option>
										<option value="Avelina"></option>
										<option value="Basilia"></option>
										<option value="Beatrice"></option>
										<option value="Cassandra"></option>
										<option value="Cecil"></option>
										<option value="Clemencia"></option>
										<option value="Desiderata"></option>
										<option value="Dionisia"></option>
										<option value="Edith"></option>
										<option value="Eleanora"></option>
										<option value="Elizabeth"></option>
										<option value="Emma"></option>
										<option value="Felicia"></option>
										<option value="Florence"></option>
										<option value="Galiana"></option>
										<option value="Grecia"></option>
										<option value="Helen"></option>
										<option value="Helewisa"></option>
										<option value="Idonea"></option>
										<option value="Isabel"></option>
										<option value="Joan"></option>
										<option value="Juliana"></option>
										<option value="Karla"></option>
										<option value="Karyn"></option>
										<option value="Kate"></option>
										<option value="Lakisha"></option>
										<option value="Lana"></option>
										<option value="Laura"></option>
										<option value="Leona"></option>
										<option value="Mandy"></option>
										<option value="Margaret"></option>
										<option value="Maria"></option>
										<option value="Nanacy"></option>
										<option value="Nicole"></option>
										<option value="Olga"></option>
										<option value="Pamela"></option>
										<option value="Patricia"></option>
										<option value="Qiana"></option>
										<option value="Rachel"></option>
										<option value="Ramona"></option>
										<option value="Samantha"></option>
										<option value="Sandra"></option>
										<option value="Tanya"></option>
										<option value="Teresa"></option>
										<option value="Ursula"></option>
										<option value="Valerie"></option>
										<option value="Veronica"></option>
										<option value="Wilma"></option>
										<option value="Yasmin"></option>
										<option value="Zelma"></option>
									</datalist>
								</label>
								<div class="note"><strong>Note:</strong> works in Chrome, Firefox, Opera and IE10.</div>
							</section>
						</fieldset>

						<fieldset>
							<section>
								<label class="label">Select</label>
								<label class="select">
									<select>
										<option value="0">Choose name</option>
										<option value="1">Alexandra</option>
										<option value="2">Alice</option>
										<option value="3">Anastasia</option>
										<option value="4">Avelina</option>
									</select>
									<i></i>
								</label>
							</section>

							<section>
								<label class="label">Multiple select</label>
								<label class="select select-multiple">
									<select multiple>
										<option value="1">Alexandra</option>
										<option value="2">Alice</option>
										<option value="3">Anastasia</option>
										<option value="4">Avelina</option>
										<option value="5">Basilia</option>
										<option value="6">Beatrice</option>
										<option value="7">Cassandra</option>
										<option value="8">Clemencia</option>
										<option value="9">Desiderata</option>
									</select>
								</label>
								<div class="note"><strong>Note:</strong> hold down the ctrl/cmd button to select multiple options.</div>
							</section>
						</fieldset>

						<fieldset>
							<section>
								<label class="label">Textarea</label>
								<label class="textarea">
									<textarea rows="3"></textarea>
								</label>
								<div class="note"><strong>Note:</strong> height of the textarea depends on the rows attribute.</div>
							</section>

							<section>
								<label class="label">Textarea resizable</label>
								<label class="textarea textarea-resizable">
									<textarea rows="3"></textarea>
								</label>
							</section>

							<section>
								<label class="label">Textarea expandable</label>
								<label class="textarea textarea-expandable">
									<textarea rows="3"></textarea>
								</label>
								<div class="note"><strong>Note:</strong> expands on focus.</div>
							</section>
						</fieldset>

						<fieldset>
							<section>
								<label class="label">Columned radios</label>
								<div class="row">
									<div class="col col-4">
										<label class="radio"><input type="radio" name="radio" checked><i class="rounded-x"></i>Alexandra</label>
										<label class="radio"><input type="radio" name="radio"><i class="rounded-x"></i>Alice</label>
										<label class="radio"><input type="radio" name="radio"><i class="rounded-x"></i>Anastasia</label>
									</div>
									<div class="col col-4">
										<label class="radio"><input type="radio" name="radio"><i class="rounded-x"></i>Avelina</label>
										<label class="radio"><input type="radio" name="radio"><i class="rounded-x"></i>Basilia</label>
										<label class="radio"><input type="radio" name="radio"><i class="rounded-x"></i>Beatrice</label>
									</div>
									<div class="col col-4">
										<label class="radio"><input type="radio" name="radio"><i class="rounded-x"></i>Cassandra</label>
										<label class="radio"><input type="radio" name="radio"><i class="rounded-x"></i>Clemencia</label>
										<label class="radio"><input type="radio" name="radio"><i class="rounded-x"></i>Desiderata</label>
									</div>
								</div>
							</section>

							<section>
								<label class="label">Inline radios</label>
								<div class="inline-group">
									<label class="radio"><input type="radio" name="radio-inline" checked><i class="rounded-x"></i>Alexandra</label>
									<label class="radio"><input type="radio" name="radio-inline"><i class="rounded-x"></i>Alice</label>
									<label class="radio"><input type="radio" name="radio-inline"><i class="rounded-x"></i>Anastasia</label>
									<label class="radio"><input type="radio" name="radio-inline"><i class="rounded-x"></i>Avelina</label>
									<label class="radio"><input type="radio" name="radio-inline"><i class="rounded-x"></i>Beatrice</label>
								</div>
							</section>
						</fieldset>

						<fieldset>
							<section>
								<label class="label">Columned checkboxes</label>
								<div class="row">
									<div class="col col-4">
										<label class="checkbox"><input type="checkbox" name="checkbox" checked><i></i>Alexandra</label>
										<label class="checkbox"><input type="checkbox" name="checkbox"><i></i>Alice</label>
										<label class="checkbox"><input type="checkbox" name="checkbox"><i></i>Anastasia</label>
									</div>
									<div class="col col-4">
										<label class="checkbox"><input type="checkbox" name="checkbox"><i></i>Avelina</label>
										<label class="checkbox"><input type="checkbox" name="checkbox"><i></i>Basilia</label>
										<label class="checkbox"><input type="checkbox" name="checkbox"><i></i>Beatrice</label>
									</div>
									<div class="col col-4">
										<label class="checkbox"><input type="checkbox" name="checkbox"><i></i>Cassandra</label>
										<label class="checkbox"><input type="checkbox" name="checkbox"><i></i>Clemencia</label>
										<label class="checkbox"><input type="checkbox" name="checkbox"><i></i>Desiderata</label>
									</div>
								</div>
							</section>

							<section>
								<label class="label">Inline checkboxes</label>
								<div class="inline-group">
									<label class="checkbox"><input type="checkbox" name="checkbox-inline" checked><i></i>Alexandra</label>
									<label class="checkbox"><input type="checkbox" name="checkbox-inline"><i></i>Alice</label>
									<label class="checkbox"><input type="checkbox" name="checkbox-inline"><i></i>Anastasia</label>
									<label class="checkbox"><input type="checkbox" name="checkbox-inline"><i></i>Avelina</label>
									<label class="checkbox"><input type="checkbox" name="checkbox-inline"><i></i>Beatrice</label>
								</div>
							</section>
						</fieldset>

						<fieldset>
							<div class="row">
								<section class="col col-5">
									<label class="label">Toggles based on radios</label>
									<label class="toggle"><input type="radio" name="radio-toggle" checked><i class="no-rounded"></i>Alexandra</label>
									<label class="toggle"><input type="radio" name="radio-toggle"><i class="no-rounded"></i>Anastasia</label>
									<label class="toggle"><input type="radio" name="radio-toggle"><i class="no-rounded"></i>Avelina</label>
								</section>

								<div class="col col-2"></div>

								<section class="col col-5">
									<label class="label">Toggles based on checkboxes</label>
									<label class="toggle"><input type="checkbox" name="checkbox-toggle" checked><i class="no-rounded"></i>Cassandra</label>
									<label class="toggle"><input type="checkbox" name="checkbox-toggle"><i class="no-rounded"></i>Clemencia</label>
									<label class="toggle"><input type="checkbox" name="checkbox-toggle"><i class="no-rounded"></i>Desiderata</label>
								</section>
							</div>
						</fieldset>

						<fieldset>
							<div class="row">
								<section class="col col-5">
									<label class="label">Toggles based on radios</label>
									<label class="toggle"><input type="radio" name="radio-toggle-1" checked><i></i>JavaScript</label>
									<label class="toggle"><input type="radio" name="radio-toggle-1"><i></i>CSS3/HTML5</label>
									<label class="toggle"><input type="radio" name="radio-toggle-1"><i></i>Bootstrap</label>
								</section>

								<div class="col col-2"></div>

								<section class="col col-5">
									<label class="label">Toggles based on checkboxes</label>
									<label class="toggle"><input type="checkbox" name="checkbox-toggle-1" checked><i></i>Canada</label>
									<label class="toggle"><input type="checkbox" name="checkbox-toggle-1"><i></i>USA</label>
									<label class="toggle"><input type="checkbox" name="checkbox-toggle-1"><i></i>Russia</label>
								</section>
							</div>
						</fieldset>

						<fieldset>
							<section>
								<label class="label">Ratings with different icons</label>
								<div class="rating">
									<input type="radio" name="stars-rating" id="stars-rating-5">
									<label for="stars-rating-5"><i class="fa fa-star"></i></label>
									<input type="radio" name="stars-rating" id="stars-rating-4">
									<label for="stars-rating-4"><i class="fa fa-star"></i></label>
									<input type="radio" name="stars-rating" id="stars-rating-3">
									<label for="stars-rating-3"><i class="fa fa-star"></i></label>
									<input type="radio" name="stars-rating" id="stars-rating-2">
									<label for="stars-rating-2"><i class="fa fa-star"></i></label>
									<input type="radio" name="stars-rating" id="stars-rating-1">
									<label for="stars-rating-1"><i class="fa fa-star"></i></label>
									Stars
								</div>

								<div class="rating">
									<input type="radio" name="trophies-rating" id="trophies-rating-7">
									<label for="trophies-rating-7"><i class="fa fa-trophy"></i></label>
									<input type="radio" name="trophies-rating" id="trophies-rating-6">
									<label for="trophies-rating-6"><i class="fa fa-trophy"></i></label>
									<input type="radio" name="trophies-rating" id="trophies-rating-5">
									<label for="trophies-rating-5"><i class="fa fa-trophy"></i></label>
									<input type="radio" name="trophies-rating" id="trophies-rating-4">
									<label for="trophies-rating-4"><i class="fa fa-trophy"></i></label>
									<input type="radio" name="trophies-rating" id="trophies-rating-3">
									<label for="trophies-rating-3"><i class="fa fa-trophy"></i></label>
									<input type="radio" name="trophies-rating" id="trophies-rating-2">
									<label for="trophies-rating-2"><i class="fa fa-trophy"></i></label>
									<input type="radio" name="trophies-rating" id="trophies-rating-1">
									<label for="trophies-rating-1"><i class="fa fa-trophy"></i></label>
									Trophies
								</div>

								<div class="rating">
									<input type="radio" name="asterisks-rating" id="asterisks-rating-10">
									<label for="asterisks-rating-10"><i class="fa fa-asterisk"></i></label>
									<input type="radio" name="asterisks-rating" id="asterisks-rating-9">
									<label for="asterisks-rating-9"><i class="fa fa-asterisk"></i></label>
									<input type="radio" name="asterisks-rating" id="asterisks-rating-8">
									<label for="asterisks-rating-8"><i class="fa fa-asterisk"></i></label>
									<input type="radio" name="asterisks-rating" id="asterisks-rating-7">
									<label for="asterisks-rating-7"><i class="fa fa-asterisk"></i></label>
									<input type="radio" name="asterisks-rating" id="asterisks-rating-6">
									<label for="asterisks-rating-6"><i class="fa fa-asterisk"></i></label>
									<input type="radio" name="asterisks-rating" id="asterisks-rating-5">
									<label for="asterisks-rating-5"><i class="fa fa-asterisk"></i></label>
									<input type="radio" name="asterisks-rating" id="asterisks-rating-4">
									<label for="asterisks-rating-4"><i class="fa fa-asterisk"></i></label>
									<input type="radio" name="asterisks-rating" id="asterisks-rating-3">
									<label for="asterisks-rating-3"><i class="fa fa-asterisk"></i></label>
									<input type="radio" name="asterisks-rating" id="asterisks-rating-2">
									<label for="asterisks-rating-2"><i class="fa fa-asterisk"></i></label>
									<input type="radio" name="asterisks-rating" id="asterisks-rating-1">
									<label for="asterisks-rating-1"><i class="fa fa-asterisk"></i></label>
									Asterisks
								</div>
								<div class="note"><strong>Note:</strong> you can use more than 350 icons for rating <a target="_blank" href="http://fortawesome.github.io/Font-Awesome">http://fortawesome.github.io/Font-Awesome</a></div>
							</section>
						</fieldset>

						<footer>
							<button type="submit" class="btn-u">Submit</button>
							<button type="button" class="btn-u btn-u-default" onclick="window.history.back();">Back</button>
						</footer>
					</form>
					<!-- General Unify Forms -->

					<div class="margin-bottom-60"></div>

					<!-- Tabs -->
					<div class="tab-v1 margin-bottom-60">
						<ul class="nav nav-tabs">
							<li class="active"><a href="#home-1" data-toggle="tab">Form Icons and Placeholders</a></li>
							<li><a href="#profile-1" data-toggle="tab">Form Tooltips</a></li>
						</ul>
						<div class="tab-content">
							<!-- Icons and Placeholders -->
							<div class="tab-pane fade in active" id="home-1">
								<form action="#" class="sky-form">
									<header>Form Icons and placeholders</header>

									<fieldset>
										<section>
											<label class="label">Text input with placeholder and appended icon</label>
											<label class="input">
												<i class="icon-append fa fa-user"></i>
												<input type="text" placeholder="Placeholder text">
											</label>
										</section>

										<section>
											<label class="label">Text input with placeholder and prepended icon</label>
											<label class="input">
												<i class="icon-prepend fa fa-user"></i>
												<input type="text" placeholder="Placeholder text">
											</label>
										</section>

										<section>
											<label class="label">Text input with placeholder and both icons</label>
											<label class="input">
												<i class="icon-prepend fa fa-user"></i>
												<i class="icon-append fa fa-asterisk"></i>
												<input type="text" placeholder="Placeholder text">
											</label>
										</section>
									</fieldset>

									<fieldset>
										<section>
											<label class="label">Textarea with placeholder and appended icon</label>
											<label class="textarea">
												<i class="icon-append fa fa-comment"></i>
												<textarea rows="3" placeholder="Placeholder text"></textarea>
											</label>
										</section>

										<section>
											<label class="label">Textarea with placeholder and prepended icon</label>
											<label class="textarea">
												<i class="icon-prepend fa fa-comment"></i>
												<textarea rows="3" placeholder="Placeholder text"></textarea>
											</label>
										</section>

										<section>
											<label class="label">Textarea with placeholder and both icons</label>
											<label class="textarea">
												<i class="icon-prepend fa fa-user"></i>
												<i class="icon-append fa fa-asterisk"></i>
												<textarea rows="3" placeholder="Placeholder text"></textarea>
											</label>
										</section>
									</fieldset>

									<footer>
										<button type="submit" class="btn-u btn-u-default">Submit</button>
										<button type="button" class="btn-u" onclick="window.history.back();">Back</button>
									</footer>
								</form>
							</div>
							<!-- End Icons and Placeholders -->

							<!-- Tooltips -->
							<div class="tab-pane fade" id="profile-1">
								<form action="#" class="sky-form">
									<header>Form Tooltips</header>

									<fieldset>
										<section>
											<label class="label">Text input with top-right tooltip</label>
											<label class="input">
												<i class="icon-append fa fa-question-circle"></i>
												<input type="text" placeholder="Focus to view the tooltip">
												<b class="tooltip tooltip-top-right">Some helpful information</b>
											</label>
										</section>

										<section>
											<label class="label">Text input with top-left tooltip</label>
											<label class="input">
												<i class="icon-prepend fa fa-question-circle"></i>
												<input type="text" placeholder="Focus to view the tooltip">
												<b class="tooltip tooltip-top-left">Some helpful information</b>
											</label>
										</section>

										<section>
											<label class="label">Text input with bottom-right tooltip</label>
											<label class="input">
												<i class="icon-append fa fa-question-circle"></i>
												<input type="text" placeholder="Focus to view the tooltip">
												<b class="tooltip tooltip-bottom-right">Some helpful information</b>
											</label>
										</section>

										<section>
											<label class="label">Text input with bottom-left tooltip</label>
											<label class="input">
												<i class="icon-prepend fa fa-question-circle"></i>
												<input type="text" placeholder="Focus to view the tooltip">
												<b class="tooltip tooltip-bottom-left">Some helpful information</b>
											</label>
										</section>

										<section>
											<label class="label">Text input with right tooltip</label>
											<label class="input">
												<i class="icon-append fa fa-question-circle"></i>
												<input type="text" placeholder="Focus to view the tooltip">
												<b class="tooltip tooltip-right">Some helpful information</b>
											</label>
										</section>

										<section>
											<label class="label">Text input with left tooltip</label>
											<label class="input">
												<i class="icon-prepend fa fa-question-circle"></i>
												<input type="text" placeholder="Focus to view the tooltip">
												<b class="tooltip tooltip-left">Some helpful information</b>
											</label>
										</section>
									</fieldset>

									<fieldset>
										<section>
											<label class="label">Textarea with top-right tooltip</label>
											<label class="textarea">
												<i class="icon-append fa fa-question-circle"></i>
												<textarea rows="3" placeholder="Focus to view the tooltip"></textarea>
												<b class="tooltip tooltip-top-right">Some helpful information</b>
											</label>
										</section>

										<section>
											<label class="label">Textarea with top-left tooltip</label>
											<label class="textarea">
												<i class="icon-prepend fa fa-question-circle"></i>
												<textarea rows="3" placeholder="Focus to view the tooltip"></textarea>
												<b class="tooltip tooltip-top-left">Some helpful information</b>
											</label>
										</section>

										<section>
											<label class="label">Textarea with bottom-right tooltip</label>
											<label class="textarea">
												<i class="icon-append fa fa-question-circle"></i>
												<textarea rows="3" placeholder="Focus to view the tooltip"></textarea>
												<b class="tooltip tooltip-bottom-right">Some helpful information</b>
											</label>
										</section>

										<section>
											<label class="label">Textarea with bottom-left tooltip</label>
											<label class="textarea">
												<i class="icon-prepend fa fa-question-circle"></i>
												<textarea rows="3" placeholder="Focus to view the tooltip"></textarea>
												<b class="tooltip tooltip-bottom-left">Some helpful information</b>
											</label>
										</section>

										<section>
											<label class="label">Textarea with right tooltip</label>
											<label class="textarea">
												<i class="icon-append fa fa-question-circle"></i>
												<textarea rows="3" placeholder="Focus to view the tooltip"></textarea>
												<b class="tooltip tooltip-right">Some helpful information</b>
											</label>
										</section>

										<section>
											<label class="label">Textarea with left tooltip</label>
											<label class="textarea">
												<i class="icon-prepend fa fa-question-circle"></i>
												<textarea rows="3" placeholder="Focus to view the tooltip"></textarea>
												<b class="tooltip tooltip-left">Some helpful information</b>
											</label>
										</section>
									</fieldset>

									<footer>
										<button type="submit" class="btn-u btn-u-default">Submit</button>
										<button type="button" class="btn-u" onclick="window.history.back();">Back</button>
									</footer>
								</form>
							</div>
							<!-- End Tooltips -->
						</div>
					</div>
					<!-- End Tabs -->

					<!-- Responsive Grid System  -->
					<form action="#" class="sky-form">
						<header>Responsive grid system</header>

						<fieldset>
							<div class="row">
								<section class="col col-2">
									<label class="input">
										<input type="text" placeholder="2/12">
									</label>
								</section>
								<section class="col col-2">
									<label class="input">
										<input type="text" placeholder="2/12">
									</label>
								</section>
								<section class="col col-2">
									<label class="input">
										<input type="text" placeholder="2/12">
									</label>
								</section>
								<section class="col col-2">
									<label class="input">
										<input type="text" placeholder="2/12">
									</label>
								</section>
								<section class="col col-2">
									<label class="input">
										<input type="text" placeholder="2/12">
									</label>
								</section>
								<section class="col col-2">
									<label class="input">
										<input type="text" placeholder="2/12">
									</label>
								</section>
							</div>

							<div class="row">
								<section class="col col-3">
									<label class="input">
										<input type="text" placeholder="3/12">
									</label>
								</section>
								<section class="col col-3">
									<label class="input">
										<input type="text" placeholder="3/12">
									</label>
								</section>
								<section class="col col-3">
									<label class="input">
										<input type="text" placeholder="3/12">
									</label>
								</section>
								<section class="col col-3">
									<label class="input">
										<input type="text" placeholder="3/12">
									</label>
								</section>
							</div>

							<div class="row">
								<section class="col col-4">
									<label class="input">
										<input type="text" placeholder="4/12">
									</label>
								</section>
								<section class="col col-4">
									<label class="input">
										<input type="text" placeholder="4/12">
									</label>
								</section>
								<section class="col col-4">
									<label class="input">
										<input type="text" placeholder="4/12">
									</label>
								</section>
							</div>

							<div class="row">
								<section class="col col-6">
									<label class="input">
										<input type="text" placeholder="6/12">
									</label>
								</section>
								<section class="col col-6">
									<label class="input">
										<input type="text" placeholder="6/12">
									</label>
								</section>
							</div>

							<section>
								<label class="input">
									<input type="text" placeholder="12/12">
								</label>
							</section>
						</fieldset>

						<fieldset>
							<div class="row">
								<section class="col col-2">
									<label class="input">
										<input type="text" placeholder="2/12">
									</label>
								</section>
								<section class="col col-2">
									<label class="input">
										<input type="text" placeholder="2/12">
									</label>
								</section>
								<section class="col col-2">
									<label class="input">
										<input type="text" placeholder="2/12">
									</label>
								</section>
								<section class="col col-3">
									<label class="input">
										<input type="text" placeholder="3/12">
									</label>
								</section>
								<section class="col col-3">
									<label class="input">
										<input type="text" placeholder="3/12">
									</label>
								</section>
							</div>

							<div class="row">
								<section class="col col-2">
									<label class="input">
										<input type="text" placeholder="2/12">
									</label>
								</section>
								<section class="col col-2">
									<label class="input">
										<input type="text" placeholder="2/12">
									</label>
								</section>
								<section class="col col-4">
									<label class="input">
										<input type="text" placeholder="4/12">
									</label>
								</section>
								<section class="col col-4">
									<label class="input">
										<input type="text" placeholder="4/12">
									</label>
								</section>
							</div>

							<div class="row">
								<section class="col col-2">
									<label class="input">
										<input type="text" placeholder="2/12">
									</label>
								</section>
								<section class="col col-4">
									<label class="input">
										<input type="text" placeholder="4/12">
									</label>
								</section>
								<section class="col col-6">
									<label class="input">
										<input type="text" placeholder="6/12">
									</label>
								</section>
							</div>
						</fieldset>
					</form>
					<!-- End Responsive Grid System -->
				</div>
				<!-- End Content -->
		</div>


		<jsp:include page="/includes/footer.jsp"></jsp:include>
	</div>


	<!-- JS Global Compulsory -->
	<script type="text/javascript" src="<%=baseURL %>assets/plugins/jquery/jquery.min.js"></script>
	<script type="text/javascript" src="<%=baseURL %>assets/plugins/jquery/jquery-migrate.min.js"></script>
	<script type="text/javascript" src="<%=baseURL %>assets/plugins/bootstrap/js/bootstrap.min.js"></script>
	<!-- JS Implementing Plugins -->
	<script type="text/javascript" src="<%=baseURL %>assets/plugins/back-to-top.js"></script>
	<script type="text/javascript" src="<%=baseURL %>assets/plugins/smoothScroll.js"></script>
	<!-- JS Customization -->
	<script type="text/javascript" src="<%=baseURL %>assets/js/custom.js"></script>
	<!-- JS Page Level -->
	<script type="text/javascript" src="<%=baseURL %>assets/js/app.js"></script>
	<script src="<%=baseURL%>assets/plugins/datatables/jquery.dataTables.min.js"></script>
	<script src="<%=baseURL%>assets/plugins/datatables/dataTables.colVis.min.js"></script>
	<script src="<%=baseURL%>assets/plugins/datatables/dataTables.tableTools.min.js"></script>
	<script src="<%=baseURL%>assets/plugins/datatables/dataTables.bootstrap.min.js"></script>
	<script src="<%=baseURL%>assets/plugins/datatable-responsive/datatables.responsive.min.js"></script>
	<script type="text/javascript">
	var responsiveHelper_dt_basic = undefined;
	var responsiveHelper_datatable_fixed_column = undefined;
	var responsiveHelper_datatable_col_reorder = undefined;
	var responsiveHelper_datatable_tabletools = undefined;
	
	var breakpointDefinition = {
		tablet : 1024,
		phone : 480
	};
	jQuery(document).ready(function() {
			App.init();

			 var otable =  $('#datatable_fixed_column').DataTable({
				 
					"sDom": "<'dt-toolbar'<'col-xs-12 col-sm-6 hidden-xs'f><'col-sm-6 col-xs-12 hidden-xs'<'toolbar'>>r>"+
							"t"+
							"<'dt-toolbar-footer'<'col-sm-6 col-xs-12 hidden-xs'i><'col-xs-12 col-sm-6'p>>",
					"autoWidth" : true,
					"preDrawCallback" : function() {
						// Initialize the responsive datatables helper once.
						if (!responsiveHelper_datatable_fixed_column) {
							responsiveHelper_datatable_fixed_column = new ResponsiveDatatablesHelper($('#datatable_fixed_column'), breakpointDefinition);
						}
					},
					"rowCallback" : function(nRow) {
						responsiveHelper_datatable_fixed_column.createExpandIcon(nRow);
					},
					"drawCallback" : function(oSettings) {
						responsiveHelper_datatable_fixed_column.respond();
					}		
					// custom toolbar
				    //$("div.toolbar").html('<div class="text-right"><img src="img/logo.png" alt="SmartAdmin" style="width: 111px; margin-top: 3px; margin-right: 10px;"></div>');
				    	   
				    // Apply the filter
				    

			    });
				   
				 $("#datatable_fixed_column thead th input[type=text]").on( 'keyup change', function () {
				        otable
				            .column( $(this).parent().index()+':visible' )
				            .search( this.value )
				            .draw();
				            
				    });
		});
	</script>
	<!--[if lt IE 9]>
	<script src="assets/plugins/respond.js"></script>
	<script src="assets/plugins/html5shiv.js"></script>
	<script src="assets/plugins/placeholder-IE-fixes.js"></script>
	<![endif]-->

</body>
</html>
