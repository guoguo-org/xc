import http from "./../../../base/api/public";
export const pafe_list = (page,size,params) =>{
  return http.requestQuickGet("http://localhost:31001/cms/pag/list/"+page+"/"+size);
}
