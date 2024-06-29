import { useState } from "react";
import { CourseResponse  } from "../type";
import Dialog from "@mui/material/Dialog";
import DialogActions from "@mui/material/DialogActions";
import DialogTitle from "@mui/material/DialogTitle";
import CourseDialogContent from "./CourseDialogContent";
import { updateCourse } from "../api/coursesapi";
import {useQuery, useMutation, useQueryClient } from "@tanstack/react-query";
import Snackbar from "@mui/material/Snackbar";
import Button from "@mui/material/Button";
import DialogContent from "@mui/material/DialogContent";
import AssignInstructorContent from "./AssignInstructorContent";
import { getallinstructors } from "../api/instructorapi";
 

type EditCourseProps = {
  CourseData: CourseResponse;
};

function EditCourse({ CourseData }: EditCourseProps) {
  const [SnackBarOpen, setSnackBarOpen] = useState(false);
  const [snackBarMessage, setSnackBarMessage] = useState("");

  const [course, setCourse] = useState<CourseResponse>({
    id: 0,
    code: "",
    title: "",
    season: "",
    instructorId: 0,
    instructorName: "",
    year: "",
    status: "",
  });

  const [open, setOpen] = useState(false);

  const { data} = useQuery({
    queryKey: ["instructors"],
    queryFn: getallinstructors,
  });

  const handleClickOpen = () => {
    setCourse({
      id: CourseData.id,
      code: CourseData.code,
      title: CourseData.title,
      season: CourseData.season,
      year: CourseData.year,
      status: CourseData.status,
      instructorId: CourseData.instructorId,
      instructorName: CourseData.instructorName,
    });
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  const queryClient = useQueryClient();

  const { mutate } = useMutation(updateCourse, {
    onSuccess: () => {
      setSnackBarOpen(true);
      queryClient.invalidateQueries(["courses"]);
    },
    onError: (err) => {
      console.error(err);
    },
  });

  const handleSave = () => {
    setSnackBarMessage(`Course ${course.title} has been updated successfully`);
    mutate(course);
    setCourse({
      id: 0,
      code: "",
      title: "",
      year: "",
      status: "",
      season: "",
      instructorId: 0,
      instructorName: "",
    });
    handleClose();
  };

  const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setCourse({ ...course, [event.target.name]: event.target.value });
  };

  return (
    <>
      <Button onClick={handleClickOpen}>Edit</Button>
      <Dialog open={open} onClose={handleClose}>
        <DialogTitle>Edit Course</DialogTitle>
        <DialogContent>
          <CourseDialogContent course={course} handleChange={handleChange} />
          <AssignInstructorContent
            selectedInstructor={course.instructorId || -1}
            setSelectedInstructor={(id: number) =>
              setCourse({ ...course, instructorId: id })
            }
            data={data || []}     
          />
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose}>Cancel</Button>
          <Button onClick={handleSave}>Save</Button>
        </DialogActions>
      </Dialog>
      <Snackbar
        open={SnackBarOpen}
        autoHideDuration={5000}
        onClose={() => setSnackBarOpen(false)}
        message={snackBarMessage}
      ></Snackbar>
    </>
  );
}

export default EditCourse;
