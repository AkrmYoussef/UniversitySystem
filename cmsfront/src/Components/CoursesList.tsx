import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import AppBar from "@mui/material/AppBar";
import Toolbar from "@mui/material/Toolbar";
import Typography from "@mui/material/Typography";
import Container from "@mui/material/Container";
import { getCourses, deleteCourse } from "../api/coursesapi";
import {
  DataGrid,
  GridColDef,
  GridCellParams,
  GridToolbar,
} from "@mui/x-data-grid";
import Snackbar from "@mui/material/Snackbar";
import AddCourse from "./AddCourse";
import AssignInstructor from "./AssignInstructor";
import EditCourse from "./EditCourse";
import Button from "@mui/material/Button";

function ListOfCourses() {
  const [open, setOpen] = useState(false);
  const [assignInstructorOpen, setAssignInstructorOpen] = useState(false);
  const [selectedCourseId, setSelectedCourseId] = useState<number>(0);
  

  const queryClient = useQueryClient();

  const navigate = useNavigate();

  const { data, isLoading, isError } = useQuery({
    queryKey: ["courses"],
    queryFn: getCourses,
  });

  const { mutate } = useMutation(deleteCourse, {
    onSuccess: () => {
      setOpen(true);
      queryClient.invalidateQueries({ queryKey: ["courses"] });
    },
    onError: (err) => {
      console.error(err);
    },
  });

  const columns: GridColDef[] = [
    { field: "code", headerName: "Course code", width: 150 },
    { field: "title", headerName: "Title", width: 200 },
    { field: "status", headerName: "Status", width: 150 },
    { field: "season", headerName: "Season", width: 150 },
    { field: "year", headerName: "Year", width: 150 },
    {
      field: "edit",
      headerName: "",
      width: 90,
      sortable: false,
      filterable: false,
      disableColumnMenu: true,
      renderCell: (params: GridCellParams) => <EditCourse  CourseData={params.row}/>,
    },
    {
      field: "instructorName",
      headerName: "Instructor",
      width: 150,
      renderCell: (params) =>
        //display instructor name if it is not null , otherwise display a button to assign instructor
        params.row.instructorName ? (
          <span>{params.row.instructorName}</span>
        ) : (

          <Button
            onClick={() => {
              setSelectedCourseId(params.row.id);
              setAssignInstructorOpen(true);
            }}
            >Assign</Button>
        ),
    },
    {
      field: "action",
      headerName: "View Course Files",
      width: 150,
      sortable: false,
      filterable: false,
      renderCell: (params) => (
        <button color="primary" onClick={() => handleViewFiles(params.row.id)}>
          View
        </button>
      ),
    },
    {
      field: "delete",
      headerName: "Delete Course",
      width: 150,
      sortable: false,
      filterable: false,
      disableColumnMenu: true,
      renderCell: (params: GridCellParams) => (
        <button
          onClick={() => {
            if (
              window.confirm(
                `Are you sure you want to delete ${params.row.code} ${params.row.title}?`
              )
            ) {
              mutate(params.row.id);
            }
          }}
        >
          Delete
        </button>
      ),
    },
  ];

  const handleViewFiles = (courseId: number) => {
    navigate(`/course-files/${courseId}`);
  };

  if (isLoading) {
    return <span>Loading...</span>;
  }

  if (isError) {
    return <span>Error when fetching courses...</span>;
  }

  return (
    <Container maxWidth="xl">
      <AppBar position="static">
        <Toolbar>
          <Typography variant="h6">List Of Courses</Typography>
        </Toolbar>
      </AppBar>
      <>
        <AddCourse></AddCourse>
        <AssignInstructor
          open={assignInstructorOpen}
          handleClose={() => setAssignInstructorOpen(false)}
          courseId={selectedCourseId}
        ></AssignInstructor>
        <DataGrid
          rows={data}
          columns={columns}
          disableRowSelectionOnClick={true}
          getRowId={(row) => row._links?.self?.href || row.id}
          slots={{ toolbar: GridToolbar }}
        />
        <Snackbar
          open={open}
          autoHideDuration={3000}
          onClose={() => setOpen(false)}
          message="Course has been deleted successfully!"
        ></Snackbar>
      </>
    </Container>
  );
}

export default ListOfCourses;
