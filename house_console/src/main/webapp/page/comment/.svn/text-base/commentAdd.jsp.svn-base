<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/page/common/base.jsp" %>
<title>增加评论</title>
</head>
<body>
<div id="wrapper">
    <!-- Navigation -->
	<jsp:include page="/page/common/menu.jsp" >
		<jsp:param value="2" name="menu"/>
		<jsp:param value="4" name="index"/>
	</jsp:include>
	<div id="page-wrapper">
	    <div class="container-fluid">
	      	<!-- Page Heading -->
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header"></h1>
				</div>
			</div>
			<div class="row">
			    <div class="container-fluid">
			        <div class="col-xs-12 ">
			            <div class="panel panel-default templatemo-content-widget white-bg no-padding templatemo-overflow-hidden">
			                <div class="panel-heading templatemo-position-relative">
								<h2 class="text-uppercase">增加评论</h2>
							</div>
							<div class="row mg-top-10">
							<form action="addComment.shtml" enctype="multipart/form-data" method="post">
							    <input type="hidden" id="houseId" name="houseId" value="${houseId }">
							    <div class="form-group col-xs-12">
							        <label for="score" class="col-xs-2 control-label"><font class="star">*</font>评分</label>
							        <div class="col-xs-10">
										<input type="text" name="score" id="score" class="form-control" value="5">
									</div>
							    </div>
							    <div class="form-group col-xs-12">
							        <label for="comments" class="col-xs-2 control-label"><font class="star">*</font>评论</label>
							        <div class="col-xs-10">
							            <textarea name="comments" id="comments" class="form-control" rows="10" cols=""></textarea>
									</div>
							    </div>
							    <div class="form-group col-xs-12">
							        <label for="images" class="col-xs-2 control-label">图片</label>
							        <div class="col-xs-10">
										<input type="file" name="images" id="images" class="form-control" multiple="multiple">
									</div>
							    </div>
							    <div class="form-group col-xs-6 text-right">
									<input type="submit" class="btn  btn-style" value="提交" />
									<input type="reset" class="btn  btn-style" value="重置" />
									<input type="button" class="btn btn-style" value="返回" onclick="history.go(-1)"/>
								</div>
							</form>
							</div>
			            </div>
			        </div>
			    
			    </div>
			</div>
	    </div>
	</div>
</div>
</body>
<script type="text/javascript">
function addComment() {
	var houseId = document.getElementById("houseId");
	var score = document.getElementById("score");
	var comments = document.getElementById("comments");
	var score = document.getElementById("score");
}
</script>

</html>