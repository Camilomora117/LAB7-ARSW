
apimock=(function(){

	var mockdata=[];

	mockdata["johnconnor"]=	[
		 {author:"johnconnor","points":[{"x":150,"y":120},{"x":215,"y":115}],"name":"house"},
    	 {author:"johnconnor","points":[{"x":340,"y":240},{"x":15,"y":215}],"name":"gear"},
    	 {author:"johnconnor","points":[{"x":34,"y":76},{"x":190,"y":71}],"name":"apto"}];
    mockdata["maryweyland"]=[
		 {author:"maryweyland","points":[{"x":140,"y":140},{"x":115,"y":115}],"name":"house2"},
    	 {author:"maryweyland","points":[{"x":140,"y":140},{"x":115,"y":115}],"name":"gear2"},
    	 {author:"maryweyland","points":[{"x":123,"y":89},{"x":10,"y":111}],"name":"apto2"}];

    function addPoints(x, y, author, bpname, callback){
        var insert = {"x": x, "y":y};
        mockdata[author].find(function(e){return e.name===bpname}).points.push(insert);
        callback();
    }

	return {
	    addPoints : addPoints,

		getBlueprintsByAuthor:function(authname,callback){
			callback(mockdata[authname]);
		},

		getBlueprintByAuthorAndName:function(authname,bpname,callback){

			callback(mockdata[authname].find(function(e){return e.name===bpname}));
		}
	}

})();
