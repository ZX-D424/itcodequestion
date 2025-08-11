<!-- <template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="关联题库类目" prop="categoryId">
        <el-input
          v-model="queryParams.categoryId"
          placeholder="请输入关联题库类目"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="题目标签" prop="tagIds">
        <el-input
          v-model="queryParams.tagIds"
          placeholder="请输入题目标签"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="题目名称/标题" prop="title">
        <el-input
          v-model="queryParams.title"
          placeholder="请输入题目名称/标题"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="题目类型" prop="questionType">
        <el-select v-model="queryParams.questionType" placeholder="请选择题目类型" clearable style="width: 200px">
          <el-option
            v-for="dict in sys_question_questiontype"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable style="width: 200px">

          <el-option
            v-for="dict in sys_status_question"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="题目来源" prop="source">
        <el-input
          v-model="queryParams.source"
          placeholder="请输入题目来源"
          clearable
          @keyup.enter="handleQuery"
        />
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
          v-hasPermi="['manage:manage:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['manage:manage:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['manage:manage:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['manage:manage:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="manageList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="题目主键ID" align="center" prop="id" />
      <el-table-column label="关联题库类目" align="center" prop="categoryId" />
      <el-table-column label="题目标签" align="center" prop="tagIds" />
      <el-table-column label="题目名称/标题" align="center" prop="title" />
      <el-table-column label="题目内容" align="center" prop="content" />
      <el-table-column label="题目类型" align="center" prop="questionType">
        <template #default="scope">
          <dict-tag :options="sys_question_questiontype" :value="scope.row.questionType"/>
        </template>
      </el-table-column>
      <el-table-column label="排序值" align="center" prop="sort" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="状态" align="center" prop="status">
        <template #default="scope">
          <dict-tag :options="sys_status_question" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="题目来源" align="center" prop="source" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['manage:manage:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['manage:manage:remove']">删除</el-button>
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

    添加或修改题库管理（主，存储题目基础信息）对话框
    <el-dialog :title="title" v-model="open" width="60%"  append-to-body>

      <el-form ref="manageRef" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="关联题库类目" prop="categoryId">
          <el-input v-model="form.categoryId" placeholder="请输入关联题库类目" />
        </el-form-item>
        <el-form-item label="题目标签" prop="tagIds">
          <el-input v-model="form.tagIds" placeholder="请输入题目标签" />
        </el-form-item>
        <el-form-item label="题目" prop="title">
          <el-input v-model="form.title" placeholder="请输入题目名称/标题" />
        </el-form-item>
        <el-form-item label="题目内容">
          <editor v-model="form.content" :min-height="192"/>
        </el-form-item>
        <el-form-item label="题目类型" prop="questionType">
          <el-select v-model="form.questionType" placeholder="请选择题目类型">
            <el-option
              v-for="dict in sys_question_questiontype"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="排序值" prop="sort">
          <el-input v-model="form.sort" placeholder="请输入排序值" />
        </el-form-item>
        <el-form-item label="备注">
          <editor v-model="form.remark" :min-height="192"/>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio
              v-for="dict in sys_status_question"
              :key="dict.value"
              :label="parseInt(dict.value)"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="题目来源" prop="source">
          <el-input v-model="form.source" placeholder="请输入题目来源" />
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

<script setup name="Manage">
import { listManage, getManage, delManage, addManage, updateManage } from "@/api/manage/manage"

const { proxy } = getCurrentInstance()
const { sys_status_question, sys_question_questiontype } = proxy.useDict('sys_status_question', 'sys_question_questiontype')

const manageList = ref([])
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
    categoryId: null,
    tagIds: null,
    title: null,
    content: null,
    questionType: null,
    status: null,
    source: null
  },
  rules: {
    categoryId: [
      { required: true, message: "关联题库类目不能为空", trigger: "blur" }
    ],
    tagIds: [
      { required: true, message: "题目标签不能为空", trigger: "blur" }
    ],
    title: [
      { required: true, message: "题目名称/标题不能为空", trigger: "blur" }
    ],
    content: [
      { required: true, message: "题目内容不能为空", trigger: "blur" }
    ],
    questionType: [
      { required: true, message: "题目类型：1-选择题 2-填空题 3-简答题 4-编程题 5-判断题不能为空", trigger: "change" }
    ],
    sort: [
      { required: true, message: "排序值不能为空", trigger: "blur" }
    ],
    createBy: [
      { required: true, message: "创建者不能为空", trigger: "blur" }
    ],
    createTime: [
      { required: true, message: "创建时间不能为空", trigger: "blur" }
    ],
    status: [
      { required: true, message: "状态：1-启用 2-禁用不能为空", trigger: "change" }
    ],
  }
})

const { queryParams, form, rules } = toRefs(data)

/** 查询题库管理（主，存储题目基础信息）列表 */
function getList() {
  loading.value = true
  listManage(queryParams.value).then(response => {
    manageList.value = response.rows
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
    categoryId: null,
    tagIds: null,
    title: null,
    content: null,
    questionType: null,
    sort: null,
    createBy: null,
    createTime: null,
    updateBy: null,
    updateTime: null,
    remark: null,
    status: null,
    source: null
  }
  proxy.resetForm("manageRef")
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
  title.value = "添加题库管理（主，存储题目基础信息）"
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset()
  const _id = row.id || ids.value
  getManage(_id).then(response => {
    form.value = response.data
    open.value = true
    title.value = "修改题库管理（主，存储题目基础信息）"
  })
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["manageRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateManage(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功")
          open.value = false
          getList()
        })
      } else {
        addManage(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除题库管理（主，存储题目基础信息）编号为"' + _ids + '"的数据项？').then(function() {
    return delManage(_ids)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {})
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('manage/manage/export', {
    ...queryParams.value
  }, `manage_${new Date().getTime()}.xlsx`)
}

getList()
</script> -->




<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="100px">
      <!-- <el-form-item label="关联题库类目" prop="categoryId">
        <el-input
          v-model="queryParams.categoryId"
          placeholder="请输入关联题库类目"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item> -->
      <el-form-item label="关联题库类目" prop="categoryId" >
          <el-tree-select
            v-model="queryParams.categoryId"
            :data="categoryOptions"
            :props="{ value: 'id', label: 'name', children: 'children' }"
            value-key="id"
            placeholder="请选择关联题库类目"
            check-strictly
            style="width: 200px"
            id="categoryTreeSelect"
          />
        </el-form-item>
      <el-form-item label="题目标签" prop="tagIds">
        <!-- <el-input
          v-model="queryParams.tagIds"
          placeholder="请输入题目标签"
          clearable
          @keyup.enter="handleQuery"
        /> -->
        <el-select v-model="queryParams.tagIds" placeholder="请选择标签" clearable style="width: 200px">
          <el-option
            v-for="tag in tagNameList"
            :key="tag.id"
            :label="tag.name"
            :value="tag.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="题目名称/标题" prop="title">
        <el-input
          v-model="queryParams.title"
          placeholder="请输入题目名称/标题"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="题目类型" prop="questionType">
        <el-select v-model="queryParams.questionType" placeholder="请选择题目类型" clearable style="width: 200px">
          <el-option
            v-for="dict in sys_question_questiontype"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable style="width: 200px">

          <el-option
            v-for="dict in sys_status_question"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="题目来源" prop="source">
        <el-input
          v-model="queryParams.source"
          placeholder="请输入题目来源"
          clearable
          @keyup.enter="handleQuery"
        />
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
          v-hasPermi="['manage:manage:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['manage:manage:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['manage:manage:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['manage:manage:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="manageList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="题目主键ID" align="center" prop="id" />
      <el-table-column label="关联题库类目" align="center" prop="category" />
      <el-table-column label="题目标签" align="center" prop="tag" />
      <el-table-column label="题目名称/标题" align="center" prop="title" />
      <el-table-column label="题目内容" align="center" prop="content" />
      <el-table-column label="题目类型" align="center" prop="questionType">
        <template #default="scope">
          <dict-tag :options="sys_question_questiontype" :value="scope.row.questionType"/>
        </template>
      </el-table-column>
      <el-table-column label="排序值" align="center" prop="sort" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="状态" align="center" prop="status">
        <template #default="scope">
          <dict-tag :options="sys_status_question" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="题目来源" align="center" prop="source" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['manage:manage:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['manage:manage:remove']">删除</el-button>
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

    <!-- 添加或修改题库管理（主，存储题目基础信息）对话框 -->
    <el-dialog :title="title" v-model="open" width="60%"  append-to-body>

      <el-form ref="manageRef" :model="form" :rules="rules" label-width="120px">
        <!-- 修改这一部分：将输入框改为树状选择器 -->
        <el-form-item label="关联题库类目" prop="categoryId">
          <el-tree-select
            v-model="form.categoryId"
            :data="categoryOptions"
            :props="{ value: 'id', label: 'name', children: 'children' }"
            value-key="id"
            placeholder="请选择关联题库类目"
            check-strictly
            id="categoryTreeSelect"
          />
        </el-form-item>
        <el-form-item label="题目标签" prop="tagIds">
          <!-- <el-input v-model="form.tag" placeholder="请输入题目标签" /> -->
           <el-select v-model="form.tagIds" placeholder="请选择标签" clearable style="width: 200px">
            <el-option
              v-for="tag in tagNameList"
              :key="tag.id"
              :label="tag.name"
              :value="tag.id"
            />
        </el-select>
        </el-form-item>
        <el-form-item label="题目" prop="title">
          <el-input v-model="form.title" placeholder="请输入题目名称/标题" />
        </el-form-item>
        <el-form-item label="题目内容">
          <editor v-model="form.content" :min-height="192"/>
        </el-form-item>
        <el-form-item label="题目类型" prop="questionType">
          <el-select v-model="form.questionType" placeholder="请选择题目类型">
            <el-option
              v-for="dict in sys_question_questiontype"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="排序值" prop="sort">
          <el-input-number v-model="form.sort" controls-position="right" :min="0" />
        </el-form-item>
        <el-form-item label="备注">
          <editor v-model="form.remark" :min-height="192"/>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio
              v-for="dict in sys_status_question"
              :key="dict.value"
              :label="parseInt(dict.value)"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="题目来源" prop="source">
          <el-input v-model="form.source" placeholder="请输入题目来源" />
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

<script setup name="Manage">
import { listManage, getManage, delManage, addManage, updateManage } from "@/api/manage/manage"
import { listTag} from "@/api/tag/tag"

// 添加引入 listCategory 函数
import { listCategory } from "@/api/category/category"

const { proxy } = getCurrentInstance()
const { sys_status_question, sys_question_questiontype } = proxy.useDict('sys_status_question', 'sys_question_questiontype')

const manageList = ref([])
const open = ref(false)
const loading = ref(true)
const showSearch = ref(true)
const ids = ref([])
const single = ref(true)
const multiple = ref(true)
const total = ref(0)
const title = ref("")
// 添加 categoryOptions 用于存储类目树数据
const categoryOptions = ref([])
const tagNameList = ref([])


const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    categoryId: null,
    tagIds: null,
    title: null,
    content: null,
    questionType: null,
    status: null,
    source: null,
    categoryid: null,
    tagid: null
  },
  rules: {
    categoryId: [
      { required: true, message: "关联题库类目不能为空", trigger: "blur" }
    ],
    tagIds: [
      { required: true, message: "题目标签不能为空", trigger: "blur" }
    ],
    title: [
      { required: true, message: "题目名称/标题不能为空", trigger: "blur" }
    ],
    content: [
      { required: true, message: "题目内容不能为空", trigger: "blur" }
    ],
    questionType: [
      { required: true, message: "题目类型：1-选择题 2-填空题 3-简答题 4-编程题 5-判断题不能为空", trigger: "change" }
    ],
    sort: [
      { required: true, message: "排序值不能为空", trigger: "blur" }
    ],
    createBy: [
      { required: true, message: "创建者不能为空", trigger: "blur" }
    ],
    createTime: [
      { required: true, message: "创建时间不能为空", trigger: "blur" }
    ],
    status: [
      { required: true, message: "状态：1-启用 2-禁用不能为空", trigger: "change" }
    ],
  }
})

const { queryParams, form, rules } = toRefs(data)



/** 查询tagNameList 列表 */
function getTagNameList() {
  loading.value = true;
  listTag(queryParams.value).then(response => {
    tagNameList.value = response.rows.map(item => ({
      id: item.id,
      name: item.tagName || item.name  // 根据实际返回数据结构调整
    }));
    loading.value = false;
  }).catch(error => {
    console.error('获取标签列表失败:', error);
    tagNameList.value = [];
    loading.value = false;
  });
}



/** 查询题库管理（主，存储题目基础信息）列表 */
function getList() {
  loading.value = true
  listManage(queryParams.value).then(response => {
    manageList.value = response.rows
    total.value = response.total
    loading.value = false
  })
}

// 取消按钮
function cancel() {
  open.value = false
  reset()
  resetQuery()
}

// 表单重置
function reset() {
  form.value = {
    id: null,
    categoryId: null,
    tagIds: null,
    title: null,
    content: null,
    questionType: null,
    sort: null,
    createBy: null,
    createTime: null,
    updateBy: null,
    updateTime: null,
    remark: null,
    status: null,
    source: null
  }
  proxy.resetForm("manageRef")
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
  // 获取类目树数据
  getTreeselect()
  open.value = true
  title.value = "添加题库管理（主，存储题目基础信息）"
}

/** 修改按钮操作 */
async function handleUpdate(row) {
  reset()
  const _id = row.id || ids.value
  // await getTreeselect()
  // await getTagNameList()
  // if (_id!= null) {
  //   form.value.categoryId = row.categoryId
  //   form.value.tagIds = row.tagIds
  // }
  getManage(_id).then(response => {
    form.value =response.data
    // form.value.categoryId = response.data.category
    // form.value.tagIds = response.data.tag
    // form.value.categoryid = response.data.categoryId
    // form.value.tagid = response.data.tagIds
    open.value = true
    title.value = "修改题库管理（主，存储题目基础信息）"
  })
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["manageRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateManage(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功")
          open.value = false
          reset()
          resetQuery()
          getList()
        })
      } else {
        addManage(form.value).then(response => {
          proxy.$modal.msgSuccess("新增成功")
          open.value = false
          reset()
          resetQuery()
          getList()
        })
      }
    }
  })
}

/** 删除按钮操作 */
function handleDelete(row) {
  const _ids = row.id || ids.value
  proxy.$modal.confirm('是否确认删除题库管理（主，存储题目基础信息）编号为"' + _ids + '"的数据项？').then(function() {
    return delManage(_ids)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {})
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('manage/manage/export', {
    ...queryParams.value
  }, `manage_${new Date().getTime()}.xlsx`)
}

// 添加获取类目树结构的函数
/** 查询题库类目下拉树结构 */
function getTreeselect() {
  listCategory().then(response => {
    categoryOptions.value = []
    const data = { id: 0, name: '顶级节点', children: [] }
    data.children = proxy.handleTree(response.data, "id", "parentId")
    categoryOptions.value.push(data)
  })
}

getList()
getTagNameList()
getTreeselect()
</script>