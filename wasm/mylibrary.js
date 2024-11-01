function my_js1() {
  console.log(20);
  return 20;
}

if (typeof mergeInto !== 'undefined') mergeInto(LibraryManager.library, {
    my_js : function() {
        return my_js1();
    }
});
