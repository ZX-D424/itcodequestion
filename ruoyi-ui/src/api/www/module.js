import request from '@/utils/userRequest'

// 模块下拉数据源
export function getModuleDataListByMenuId(menuId) {
  return request({
    url: '/www/module/getModuleDataListByMenuId/'+menuId,
    method: 'get'
  })
}
