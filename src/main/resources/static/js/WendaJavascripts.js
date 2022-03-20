var dis=0;
function showdown(){
    if(dis==0){
        $('#top-nav-profile-dropdown').css("display","inline");
        dis++;
    }else{
        $('#top-nav-profile-dropdown').css("display","none");
        dis--;
    }

}