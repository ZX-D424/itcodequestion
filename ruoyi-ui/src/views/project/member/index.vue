<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="昵称绰号" prop="nickName">
        <el-input
          v-model="queryParams.nickName"
          placeholder="请输入昵称绰号"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
<!--      <el-form-item label="成员类型" prop="memberType">-->
<!--        <el-select v-model="queryParams.memberType" placeholder="请选择成员类型" clearable style="width: 200px">-->
<!--          <el-option-->
<!--            v-for="dict in member_type"-->
<!--            :key="dict.value"-->
<!--            :label="dict.label"-->
<!--            :value="dict.value"-->
<!--          />-->
<!--        </el-select>-->
<!--      </el-form-item>-->
      <el-form-item label="邮箱" prop="email">
        <el-input
          v-model="queryParams.email"
          placeholder="请输入邮箱"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
<!--      <el-form-item label="学校名称" prop="schoolName">-->
<!--        <el-input-->
<!--          v-model="queryParams.schoolName"-->
<!--          placeholder="请输入学校名称"-->
<!--          clearable-->
<!--          @keyup.enter="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="专业(字典)" prop="specialty">-->
<!--        <el-select v-model="queryParams.specialty" placeholder="请选择专业(字典)" clearable style="width: 200px">-->
<!--          <el-option-->
<!--            v-for="dict in specialty_type"-->
<!--            :key="dict.value"-->
<!--            :label="dict.label"-->
<!--            :value="dict.value"-->
<!--          />-->
<!--        </el-select>-->
<!--      </el-form-item>-->
<!--      <el-form-item label="学历" prop="degree">-->
<!--        <el-select v-model="queryParams.degree" placeholder="请选择学历" clearable style="width: 200px">-->
<!--          <el-option-->
<!--            v-for="dict in degree_type"-->
<!--            :key="dict.value"-->
<!--            :label="dict.label"-->
<!--            :value="dict.value"-->
<!--          />-->
<!--        </el-select>-->
<!--      </el-form-item>-->
      <el-form-item label="审核状态" prop="auditStatus">
        <el-select v-model="queryParams.auditStatus" placeholder="请选择审核状态" clearable style="width: 200px">
          <el-option
            v-for="dict in audit_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="Plus"
          @click="handleAdd"
          v-hasPermi="['project:member:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['project:member:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['project:member:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['project:member:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="memberList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
<!--      <el-table-column label="id" align="center" prop="id" />-->
      <el-table-column label="图片" align="center" prop="profilePicture" width="100">
        <template #default="scope">
          <image-preview :src="scope.row.profilePicture" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="昵称绰号" align="center" prop="nickName" />
      <el-table-column label="成员类型" align="center" prop="memberType">
        <template #default="scope">
          <dict-tag :options="member_type" :value="scope.row.memberType"/>
        </template>
      </el-table-column>
      <el-table-column label="擅长方向" align="center" prop="proficiencyArea" />
      <el-table-column label="邮箱" align="center" prop="email" />
<!--      <el-table-column label="个人空间" align="center" prop="personalSpace" />-->
<!--      <el-table-column label="gitee地址" align="center" prop="giteeLink" />-->
<!--      <el-table-column label="github地址" align="center" prop="githubLink" />-->
      <el-table-column label="学校名称" align="center" prop="schoolName" />
      <el-table-column label="专业" align="center" prop="specialty">
        <template #default="scope">
          <dict-tag :options="specialty_type" :value="scope.row.specialty"/>
        </template>
      </el-table-column>
      <el-table-column label="学历" align="center" prop="degree">
        <template #default="scope">
          <dict-tag :options="degree_type" :value="scope.row.degree"/>
        </template>
      </el-table-column>
      <el-table-column label="审核状态" align="center" prop="auditStatus">
        <template #default="scope">
          <dict-tag :options="audit_status" :value="scope.row.auditStatus"/>
        </template>
      </el-table-column>
<!--      <el-table-column label="简介" align="center" prop="contentInfo" show-overflow-tooltip />-->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['project:member:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['project:member:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      v-model:page="queryParams.pageNum"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改项目成员对话框 -->
    <el-dialog :title="title" v-model="open" width="60%" append-to-body>
      <el-form ref="memberRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="图片" prop="profilePicture">
          <image-upload v-model="form.profilePicture"/>
        </el-form-item>
        <el-form-item label="昵称绰号" prop="nickName">
          <el-input v-model="form.nickName" placeholder="请输入昵称绰号" />
        </el-form-item>
        <el-form-item label="成员类型" prop="memberType">
          <el-select v-model="form.memberType" placeholder="请选择成员类型">
            <el-option
              v-for="dict in member_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="擅长方向" prop="proficiencyArea">
          <el-input v-model="form.proficiencyArea" placeholder="请输入擅长方向" />
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="form.email" placeholder="请输入邮箱" />
        </el-form-item>
        <el-form-item label="个人空间" prop="personalSpace">
          <el-input v-model="form.personalSpace" placeholder="请输入个人空间" />
        </el-form-item>
        <el-form-item label="gitee" prop="giteeLink">
          <el-input v-model="form.giteeLink" placeholder="请输入gitee地址" />
        </el-form-item>
        <el-form-item label="github" prop="githubLink">
          <el-input v-model="form.githubLink" placeholder="请输入github地址" />
        </el-form-item>
        <el-form-item label="学校名称" prop="schoolName">
          <el-input v-model="form.schoolName" placeholder="请输入学校名称" />
        </el-form-item>
        <el-form-item label="专业(字典)" prop="specialty">
          <el-select v-model="form.specialty" placeholder="请选择专业(字典)">
            <el-option
              v-for="dict in specialty_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="学历" prop="degree">
          <el-select v-model="form.degree" placeholder="请选择学历">
            <el-option
              v-for="dict in degree_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="审核状态" prop="auditStatus">
          <el-select v-model="form.auditStatus" placeholder="请选择审核状态">
            <el-option
              v-for="dict in audit_status"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="简介" prop="contentInfo">
          <el-input v-model="form.contentInfo" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="Member">
import { listMember, getMember, delMember, addMember, updateMember } from "@/api/project/member"

const { proxy } = getCurrentInstance()
const { audit_status, specialty_type, member_type, degree_type } = proxy.useDict('audit_status', 'specialty_type', 'member_type', 'degree_type')

const memberList = ref([])
const open = ref(false)
const loading = ref(true)
const showSearch = ref(true)
const ids = ref([])
const single = ref(true)
const multiple = ref(true)
const total = ref(0)
const title = ref("")

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    nickName: null,
    memberType: null,
    email: null,
    schoolName: null,
    specialty: null,
    degree: null,
    auditStatus: null,
  },
  rules: {
  }
})

const { queryParams, form, rules } = toRefs(data)

/** 查询项目成员列表 */
function getList() {
  loading.value = true
  listMember(queryParams.value).then(response => {
    memberList.value = response.rows
    total.value = response.total
    loading.value = false
  })
}

// 取消按钮
function cancel() {
  open.value = false
  reset()
}

// 表单重置
function reset() {
  form.value = {
    id: null,
    profilePicture: null,
    nickName: null,
    memberType: null,
    proficiencyArea: null,
    email: null,
    personalSpace: null,
    giteeLink: null,
    githubLink: null,
    schoolName: null,
    specialty: null,
    degree: null,
    auditStatus: null,
    contentInfo: null,
    createBy: null,
    createTime: null,
    updateBy: null,
    updateTime: null
  }
  proxy.resetForm("memberRef")
}

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1
  getList()
}

/** 重置按钮操作 */
function resetQuery() {
  proxy.resetForm("queryRef")
  handleQuery()
}

// 多选框选中数据
function handleSelectionChange(selection) {
  ids.value = selection.map(item => item.id)
  single.value = selection.length != 1
  multiple.value = !selection.length
}

/** 新增按钮操作 */
function handleAdd() {
  reset()
  open.value = true
  title.value = "添加项目成员"
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset()
  const _id = row.id || ids.value
  getMember(_id).then(response => {
    form.value = response.data
    open.value = true
    title.value = "修改项目成员"
  })
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["memberRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateMember(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功")
          open.value = false
          getList()
        })
      } else {
        addMember(form.value).then(response => {
          proxy.$modal.msgSuccess("新增成功")
          open.value = false
          getList()
        })
      }
    }
  })
}

/** 删除按钮操作 */
function handleDelete(row) {
  const _ids = row.id || ids.value
  proxy.$modal.confirm('是否确认删除项目成员编号为"' + _ids + '"的数据项？').then(function() {
    return delMember(_ids)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {})
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('project/member/export', {
    ...queryParams.value
  }, `member_${new Date().getTime()}.xlsx`)
}

getList()
</script>
