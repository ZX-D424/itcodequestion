import request from '@/utils/userRequest'

//获取菜单列表下拉框数据
export function getMemberDataList() {
  return request({
    url: '/www/member/getMemberDataList',
    method: 'get',
  })
}
