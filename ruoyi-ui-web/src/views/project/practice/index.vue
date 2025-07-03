<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入名称"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="所属模块" prop="moduleId">
        <el-select v-model="queryParams.moduleId" placeholder="请选择所属模块" style="width: 200px;" >
          <el-option
              v-for="module in moduleDataList"
              :key="module.id"
              :label="module.name"
              :value="module.id"
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
          v-hasPermi="['project:practice:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['project:practice:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['project:practice:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['project:practice:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="practiceList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
<!--      <el-table-column label="ID" align="center" prop="id" />-->
      <el-table-column label="所属模块" align="center" prop="moduleName" />
      <el-table-column label="名称" align="center" prop="name" />
<!--      <el-table-column label="项目描述" align="center" prop="content" show-overflow-tooltip />-->
      <el-table-column label="价格" align="center" prop="price" >
        <template #default="scope">
            <span v-if="scope.row.price ===0" style="color:blue;" >免费 </span>
            <span v-if="scope.row.price > 0" style="color:red;" > {{scope.row.price }} </span>
        </template>
      </el-table-column>
      <el-table-column label="排序值" align="center" prop="numSort" />
      <el-table-column label="QQ号码" align="center" prop="qqNumber" />
      <el-table-column label="邮箱" align="center" prop="emailNumber" />
      <el-table-column label="封面图" align="center" prop="imgUrl" width="100">
        <template #default="scope">
          <image-preview :src="scope.row.imgUrl" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="二维码" align="center" prop="qrCode" width="100">
        <template #default="scope">
          <image-preview :src="scope.row.qrCode" :width="50" :height="50"/>
        </template>
      </el-table-column>

<!--      <el-table-column label="附件链接" align="center" prop="fileUrl" />-->
     <el-table-column label="创建者" align="center" prop="createBy" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['project:practice:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['project:practice:remove']">删除</el-button>
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

    <!-- 添加或修改练习项目对话框 -->
    <el-dialog :title="title" v-model="open" width="60%" append-to-body>
      <el-form ref="practiceRef" :model="form" :rules="rules" label-width="80px">


        <el-form-item label="所属模块" prop="moduleId">
          <el-select v-model="form.moduleId" placeholder="请选择所属模块" >
            <el-option
                v-for="module in moduleDataList"
                :key="module.id"
                :label="module.name"
                :value="module.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入名称" />
        </el-form-item>
        <el-form-item label="邮箱" prop="numSort">
          <el-input v-model="form.emailNumber" placeholder="请输入邮箱地址" />
        </el-form-item>
        <div style="display: flex;justify-content: space-between">
          <el-form-item label="价格" prop="price">
            <el-input-number v-model="form.price" placeholder="请输入价格" />
          </el-form-item>
          <el-form-item label="排序值" prop="numSort">
            <el-input-number v-model="form.numSort" placeholder="请输入排序值" />
          </el-form-item>
          <el-form-item label="QQ号码" prop="numSort">
            <el-input-number v-model="form.qqNumber" placeholder="请输入QQ号码" />
          </el-form-item>
        </div>
        <div style="display: flex;justify-content: space-between">
        <el-form-item label="图片路径" prop="imgUrl">
          <image-upload v-model="form.imgUrl"/>
        </el-form-item>
          <el-form-item label="二维码" prop="qrCode">
            <image-upload v-model="form.qrCode"/>
          </el-form-item>
        <el-form-item label="附件链接" prop="fileUrl">
          <file-upload v-model="form.fileUrl"/>
        </el-form-item>
        </div>
        <el-form-item label="项目描述">
          <editor v-model="form.content" :min-height="192"/>
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

<script setup name="Practice">
import { listPractice, getPractice, delPractice, addPractice, updatePractice } from "@/api/project/practice"
import { getModuleDataList } from "@/api/project/module.js"

const { proxy } = getCurrentInstance()

const practiceList = ref([])
const open = ref(false)
const loading = ref(true)
const showSearch = ref(true)
const ids = ref([])
const single = ref(true)
const multiple = ref(true)
const total = ref(0)
const title = ref("")
const moduleDataList = ref([])
const moduleTypeParams = ref(1);

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    moduleId: null,
    name: null,
  },
  rules: {
  }
})

const { queryParams, form, rules } = toRefs(data)

/** 查询练习项目列表 */
function getList() {
  loading.value = true
  listPractice(queryParams.value).then(response => {
    practiceList.value = response.rows
    total.value = response.total
    loading.value = false
  })
}

/** 查询练习项目列表 */
function initModuleDataList() {
  loading.value = true
  getModuleDataList(moduleTypeParams.value).then(response => {
    moduleDataList.value = response.data
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
    moduleId: null,
    name: null,
    content: null,
    price: null,
    imgUrl: null,
    fileUrl: null,
    numSort: null,
    qqNumber: null,
    emailNumber: null,
    qrCode: null,
  }
  proxy.resetForm("practiceRef")
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
  title.value = "添加练习项目"
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset()
  const _id = row.id || ids.value
  getPractice(_id).then(response => {
    form.value = response.data
    open.value = true
    title.value = "修改练习项目"
  })
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["practiceRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updatePractice(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功")
          open.value = false
          getList()
        })
      } else {
        addPractice(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除练习项目编号为"' + _ids + '"的数据项？').then(function() {
    return delPractice(_ids)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {})
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('project/practice/export', {
    ...queryParams.value
  }, `practice_${new Date().getTime()}.xlsx`)
}

getList();
initModuleDataList();
</script>
